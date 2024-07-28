package com.example.surveybackend.survey.app.backend.Services;

import com.example.surveybackend.survey.app.backend.Dtos.ResponseDtos.ResponseDto;
import com.example.surveybackend.survey.app.backend.Dtos.SurveyDtos.SurveyDto;
import com.example.surveybackend.survey.app.backend.Dtos.SurveyDtos.SurveyListDto;
import com.example.surveybackend.survey.app.backend.Dtos.ResponseDtos.SurveyResponsesDto;
import com.example.surveybackend.survey.app.backend.Entities.ResponseQueueEntity;
import com.example.surveybackend.survey.app.backend.Entities.SurveyEntity;
import com.example.surveybackend.survey.app.backend.Entities.UserEntity;
import com.example.surveybackend.survey.app.backend.Model.SurveyAndQuestionMerged;
import com.example.surveybackend.survey.app.backend.MongoDocuments.QuestionsDocument;
import com.example.surveybackend.survey.app.backend.MongoDocuments.ResponsesDocument;
import com.example.surveybackend.survey.app.backend.Repository.*;
import com.example.surveybackend.survey.app.backend.Utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@EnableScheduling
public class SurveyService {
  @Autowired
  SurveyRepository surveyRepository;

  @PersistenceContext
  EntityManager entityManager;

  @Autowired
  QuestionRepository questionRepository;

  @Autowired
  ResponsesRepository responseRepository;

  @Autowired
  ResponseQueueTableRepository responseQueueTableRepository;

  @Autowired
  UserRepository userRepository;

  @Autowired
  QuestionService questionService;

  @Autowired
  Utils utils;

    @Transactional
    public long addSurvey(SurveyDto surveyDto)
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        if(surveyDto.getStartDate() != null && !surveyDto.getStartDate().equals(dateFormat.format(date)))
            surveyDto.setVisibility(false);

        UserEntity userEntity = userRepository.findByEmail(surveyDto.getSurveyorEmail());
        SurveyEntity surveyEntity = surveyDto.toSurveyEntity(userEntity.getId());
        SurveyEntity savedSurvey = surveyRepository.save(surveyEntity);
        entityManager.flush();
        questionService.addQuestion(savedSurvey.getId(),surveyDto.getQuestions());
        return savedSurvey.getId();
    }

    @Scheduled(cron = "0 0 0 * * *")
    void startScheduledSurvey()
    {
        Calendar calendar = Calendar.getInstance();
        java.sql.Date currentDate = new java.sql.Date(calendar.getTime().getTime());
        List<SurveyEntity> surveyEntities = surveyRepository.findByStartDate(currentDate);
        for(SurveyEntity survey :surveyEntities)
        {
            survey.setVisibility(true);
            surveyRepository.save(survey);
        }
    }

    @Scheduled(cron = "0 0 0 * * *")
    void endScheduledSurvey()
    {
        Calendar calendar = Calendar.getInstance();
        java.sql.Date currentDate = new java.sql.Date(calendar.getTime().getTime());
        List<SurveyEntity> surveyEntities = surveyRepository.findByEndDate(currentDate);
        for(SurveyEntity survey :surveyEntities)
        {
            survey.setVisibility(false);
            surveyRepository.save(survey);
        }
    }



    public List<SurveyListDto> getGeneralSurveyList()
    {
         List<SurveyEntity> compeleteSurveyList = surveyRepository.findByIsGeneralAndVisibility(true,true);
         return utils.convertToSurveyListDto(compeleteSurveyList);
    }

    public List<SurveyListDto> getSurveyListBySurveyor(String email)
    {
        UserEntity user = userRepository.findByEmail(email);
        List<SurveyEntity> compeleteSurveyList = surveyRepository.findBySurveyorId(user.getId());
        return utils.convertToSurveyListDto(compeleteSurveyList);
    }

    public SurveyAndQuestionMerged getSurveyById(long id)
    {
        SurveyEntity surveyEntity = surveyRepository.findByIdAndVisibility(id,Boolean.TRUE);
        QuestionsDocument questionsDocument = questionRepository.findBySurveyId(id);
        Optional<UserEntity> surveyor = userRepository.findById(surveyEntity.getSurveyorId());
        String surveyorMail = "Surveyor not found!";
        if(surveyor.isPresent()) surveyorMail = surveyor.get().getEmail();
        return new SurveyAndQuestionMerged(surveyEntity.getId(),surveyEntity.getSurveyName(),
                surveyEntity.getSurveyDescription(),surveyorMail,
                surveyEntity.isGeneral(),surveyEntity.isAnonymous(),surveyEntity.isVisibility(),surveyEntity.getStartDate(),
                surveyEntity.getEndDate(),surveyEntity.getTimeLimit(),surveyEntity.getTotalPoints(),
                questionsDocument.getQuestions());
    }

    public boolean isAttempted(long surveyId, String email)
    {
        UserEntity user = userRepository.findByEmail(email);
        return responseRepository.findByCandidateIdAndSurveyId(user.getId(),surveyId) != null;
    }

    @Transactional
    public void addResponse(ResponseDto responseDto)
    {
        ResponsesDocument responsesDocument;
        if(responseDto.getUserEmail() == null)
            responsesDocument = responseDto.toDocument();
        else {
            UserEntity user = userRepository.findByEmail(responseDto.getUserEmail());
            responsesDocument = responseDto.toDocument(user.getId());
        }
        ResponsesDocument savedResponse = responseRepository.save(responsesDocument);
        entityManager.flush();
        addResponseToQueue(savedResponse.getId());
    }

    void addResponseToQueue(String responseId)
    {
        responseQueueTableRepository.save(new ResponseQueueEntity(0,responseId));
    }

    public boolean closeSurvey(long id, String email)
    {
      UserEntity user = userRepository.findByEmail(email);
        if(null != user) {
          SurveyEntity surveyEntity = surveyRepository.findByIdAndSurveyorId(id, user.getId());
          if(null != surveyEntity) {
            surveyEntity.setVisibility(false);
            surveyRepository.save(surveyEntity);
            return true;
          }
          return false;
        }
        return false;
    }

    public List<SurveyResponsesDto> getResponses(long surveyId)
    {
        SurveyEntity survey = surveyRepository.findById(surveyId);
        List<ResponsesDocument> responsesDocuments = responseRepository.findBySurveyId(surveyId);
        if(survey.isAnonymous())
            return utils.getAnonymousResponses(surveyId,responsesDocuments);
        else {
            List<SurveyResponsesDto> responsesList = new ArrayList<>();
            for (ResponsesDocument response : responsesDocuments) {
                Optional<UserEntity> user = userRepository.findById(response.getCandidateId());
                user.ifPresent(userEntity -> responsesList.add(new SurveyResponsesDto(userEntity.getEmail(), userEntity.getName(), response.getSurveyId(),
                        response.getEarnedPoints(), response.getSurveyAnswers())));
            }
            return responsesList;
        }
    }

  public Page<SurveyListDto> getGeneralSurveyList(Pageable pageable) {
    Page<SurveyEntity> completeSurveyList =
        surveyRepository.findByIsGeneralAndVisibility(true, true, pageable);
    return completeSurveyList.map(SurveyListDto::new);
  }

}
