package com.example.surveybackend.survey.app.backend.Services;

import com.example.surveybackend.survey.app.backend.Dtos.CourseDto.CourseQuestionModel;
import com.example.surveybackend.survey.app.backend.Dtos.CourseDto.FeedbackSurveyDto;
import com.example.surveybackend.survey.app.backend.Dtos.ResponseDtos.ResponseDto;
import com.example.surveybackend.survey.app.backend.Entities.CourseSurveyEntity;
import com.example.surveybackend.survey.app.backend.Entities.SurveyEntity;
import com.example.surveybackend.survey.app.backend.Entities.UserEntity;
import com.example.surveybackend.survey.app.backend.Model.QuestionsModel;
import com.example.surveybackend.survey.app.backend.MongoDocuments.ResponsesDocument;
import com.example.surveybackend.survey.app.backend.MongoDocuments.ResultsDocument;
import com.example.surveybackend.survey.app.backend.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Map;

@Service
public class CourseService {

    @Autowired
    UserRepository userRepository;

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    SurveyRepository surveyRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    QuestionService questionService;

    @Autowired
    ResponsesRepository responseRepository;

    @Autowired
    ResultService resultService;

    @Autowired
    ResultRepository resultRepository;
    int[] weights = new int[] {5, 4, 3, 2, 1};


    public boolean checkUser(String email)
    {
        return userRepository.findByEmail(email) != null;
    }


    @Transactional
    public long createSurveyFeedBack(FeedbackSurveyDto feedbackSurveyDto)
    {
        UserEntity instructor = userRepository.findByEmail(feedbackSurveyDto.getInstructorEmail());
        if(instructor != null) {
            SurveyEntity surveyEntity = new SurveyEntity(0, instructor.getId(), feedbackSurveyDto.getCourseName(),
                    feedbackSurveyDto.getCourseDescription(),true,false,false,true);

            SurveyEntity savedSurvey = surveyRepository.save(surveyEntity);
            entityManager.flush();
            courseRepository.save(new CourseSurveyEntity(0,savedSurvey.getId()));
            addFeedBackSurveyQuestions(savedSurvey.getId(),feedbackSurveyDto.getQuestions());
            return savedSurvey.getId();
        }
        return 0;
    }

   void addFeedBackSurveyQuestions(long surveyId,CourseQuestionModel[] courseQuestions)
   {
       QuestionsModel[] questions = new QuestionsModel[courseQuestions.length];
       for (int i = 0; i < courseQuestions.length; i++) {
           questions[i] = new QuestionsModel();
           questions[i].setQuestion(courseQuestions[i].getQuestion());
           questions[i].setOptions(courseQuestions[i].getOptions());
           questions[i].setType("radio");
           questions[i].setRequired(true);
       }
       questionService.addQuestion(surveyId,questions);
   }

   @Transactional
   public void addFeedBackSurveyResponse(ResponseDto responseDto)
   {
       ResponsesDocument responsesDocument = responseDto.toDocument();
       ResponsesDocument savedResponse = responseRepository.save(responsesDocument);
       entityManager.flush();
       resultService.updateResult(savedResponse);
       calculateRating(responseDto.getSurveyId());
   }

   void calculateRating(long surveyId)
   {
       ResultsDocument resultsDocument = resultRepository.findBySurveyId(surveyId);
       int numberOfQuestion = 0;
       double totalRating = 0.0;
       for (Map.Entry<String, Object> entry : resultsDocument.getSurveyResults().entrySet()) {
           if(numberOfQuestion>=5)
               break;
           Object value = entry.getValue();
           int questionResponses = 0;
           double questionRating = 0.0;
           if(entry.getKey().contains("Responses")) {
               List<Object> values = (List<Object>) value;
               for(int j = 0; j < values.size(); j++)
               {
                   questionResponses += (int) values.get(j);
                   questionRating += ((int) values.get(j)) * weights[j];
               }
               numberOfQuestion++;
               totalRating += questionRating / questionResponses;
           }
       }
       sendRating(totalRating / numberOfQuestion, surveyId);
   }

    public void sendRating(double rating,long surveyId)
    {
        System.out.println(rating);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response =
                restTemplate.getForEntity("http://172.16.29.141:8091/get/rating?surveyId=" + surveyId +"&rating="+rating, String.class);
    }

}


