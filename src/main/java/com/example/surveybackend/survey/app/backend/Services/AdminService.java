package com.example.surveybackend.survey.app.backend.Services;

import com.example.surveybackend.survey.app.backend.Dtos.SurveyDtos.SurveyListDto;
import com.example.surveybackend.survey.app.backend.Dtos.UserDtos.UserDto;
import com.example.surveybackend.survey.app.backend.Entities.SurveyEntity;
import com.example.surveybackend.survey.app.backend.Entities.UserEntity;
import com.example.surveybackend.survey.app.backend.MongoDocuments.ResultsDocument;
import com.example.surveybackend.survey.app.backend.Repository.ResponsesRepository;
import com.example.surveybackend.survey.app.backend.Repository.ResultRepository;
import com.example.surveybackend.survey.app.backend.Repository.SurveyRepository;
import com.example.surveybackend.survey.app.backend.Repository.UserRepository;
import com.example.surveybackend.survey.app.backend.Utils.Utils;
import com.google.gson.reflect.TypeToken;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class AdminService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ResultRepository resultRepository;

    @Autowired
    SurveyRepository surveyRepository;

    @Autowired
    ResponsesRepository responsesRepository;

    @Autowired
    Utils utils;

    //User functions
    public List<UserDto> getUserList()
    {
        List<UserEntity> users = userRepository.findAll();
        ModelMapper modelMapper = new ModelMapper();
        Type listType = new TypeToken<List<UserDto>>() {}.getType();
        return modelMapper.map(users, listType);
    }

    public boolean removeUser(String email)
    {
        UserEntity user = userRepository.findByEmail(email);
        List<SurveyEntity> surveyEntitiesOfDeletedUser = surveyRepository.findBySurveyorId(user.getId());
        for(SurveyEntity survey : surveyEntitiesOfDeletedUser)
        {
            survey.setVisibility(false);
            surveyRepository.save(survey);
        }
        userRepository.delete(user);
        return true;
    }


    //Surveys
    public List<SurveyListDto> getAllSurveyList()
    {
        List<SurveyEntity> compeleteSurveyList = surveyRepository.findAll();
        return utils.convertToSurveyListDto(compeleteSurveyList);
    }


    public boolean removeSurvey(long surveyId)
    {
        SurveyEntity surveyEntity = surveyRepository.findById(surveyId);
        surveyRepository.delete(surveyEntity);
        responsesRepository.deleteBySurveyId(surveyId);
        resultRepository.deleteById(surveyId);
        return true;
    }

    //Response
    public boolean removeResponse(long surveyId) {
        responsesRepository.deleteBySurveyId(surveyId);
        //Reset result if exist
        ResultsDocument resultsDocument = resultRepository.findBySurveyId(surveyId);
        utils.resetResult(resultsDocument);
        resultRepository.save(resultsDocument);
        return true;
    }


    //Results
    public boolean removeResult(long surveyId)
    {
        ResultsDocument resultsDocument = resultRepository.findBySurveyId(surveyId);
        resultRepository.delete(resultsDocument);
        return true;
    }

}
