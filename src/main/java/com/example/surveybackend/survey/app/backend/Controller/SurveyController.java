package com.example.surveybackend.survey.app.backend.Controller;

import com.example.surveybackend.survey.app.backend.CommonConstants;
import com.example.surveybackend.survey.app.backend.Dtos.*;
import com.example.surveybackend.survey.app.backend.Dtos.ResponseDtos.ResponseDto;
import com.example.surveybackend.survey.app.backend.Dtos.ResponseDtos.SurveyResponsesDto;
import com.example.surveybackend.survey.app.backend.Dtos.SurveyDtos.SurveyDto;
import com.example.surveybackend.survey.app.backend.Dtos.SurveyDtos.SurveyListDto;
import com.example.surveybackend.survey.app.backend.Model.SurveyAndQuestionMerged;
import com.example.surveybackend.survey.app.backend.MongoDocuments.ResultsDocument;
import com.example.surveybackend.survey.app.backend.Repository.CourseRepository;
import com.example.surveybackend.survey.app.backend.Services.CourseService;
import com.example.surveybackend.survey.app.backend.Services.ResultService;
import com.example.surveybackend.survey.app.backend.Services.SurveyService;
import com.example.surveybackend.survey.app.backend.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class SurveyController {

  @Autowired
  SurveyService surveyService;
  @Autowired
  ResultService resultService;

  @Autowired
  UserService userService;
  @Autowired
  CourseRepository courseRepository;

  @Autowired
  CourseService courseService;

  @PostMapping("/{email}/createSurvey")
  long createSurvey(@RequestBody SurveyDto surveyDto, @PathVariable("email") String email) {
    if (!userService.validateUserForRoles(email,
        Arrays.asList(CommonConstants.ADMIN, CommonConstants.SURVEYOR))) {
      return -1;
    }
    return surveyService.addSurvey(surveyDto);
  }

  @GetMapping("/getGeneralSurveyList")
    //    List<SurveyListDto> getGeneralSurveyList(@RequestParam("page") int page,
    //                                             @RequestParam("size") int size,
    //                                             UriComponentsBuilder uriBuilder,
    //                                             HttpServletResponse response) {
  List<SurveyListDto> getGeneralSurveyList() {
    return surveyService.getGeneralSurveyList();
    // return surveyService.getGeneralSurveyList(PageRequest.of(page,size));
  }

  @GetMapping("/{email}/getSurveyListBySurveyor")
  List<SurveyListDto> getSurveyListBySurveyor(@RequestParam String email,
      @PathVariable("email") String pathEmail) {
    if (!userService.validateUserForRoles(pathEmail,
        Arrays.asList(CommonConstants.ADMIN, CommonConstants.SURVEYOR)) && !email.equals(
        pathEmail)) {
      return null;
    }
    return surveyService.getSurveyListBySurveyor(email);
  }

  @GetMapping("/getSurveyById")
  SurveyAndQuestionMerged getSurvey(@RequestParam long surveyId) {
    return surveyService.getSurveyById(surveyId);
  }

  @PostMapping("/addResponse")
  void addResponse(@RequestBody ResponseDto responseDto) {
    if (courseRepository.findBySurveyId(responseDto.getSurveyId()) != null) {
      courseService.addFeedBackSurveyResponse(responseDto);
    }
    surveyService.addResponse(responseDto);
  }

  @GetMapping("/{email}/getSurveyResponses")
  List<SurveyResponsesDto> getSurveyResponses(@RequestParam long surveyId,
      @PathVariable("email") String email) {
    if (!userService.validateUserForRoles(email,
        Arrays.asList(CommonConstants.ADMIN, CommonConstants.SURVEYOR))) {
      return null;
    }
    return surveyService.getResponses(surveyId);
  }

  @GetMapping("/{email}/getResult")
  ResponseEntity<?> getResult(@RequestParam long surveyId, @PathVariable("email") String email) {
    if (!userService.validateUserForRoles(email,
        Arrays.asList(CommonConstants.ADMIN, CommonConstants.SURVEYOR))) {
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    ResultsDocument result = resultService.getResult(surveyId);
    if (result == null) {
      return new ResponseEntity<>(new ErrorDto(CommonConstants.RESULT_NOT_FOUND),
          HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  @GetMapping("/{email}/closeSurvey")
  boolean closeSurvey(@RequestParam long surveyId, @PathVariable("email") String email) {
    if (!userService.validateUserForRoles(email,
        Arrays.asList(CommonConstants.ADMIN, CommonConstants.SURVEYOR))) {
      return false;
    }
    return surveyService.closeSurvey(surveyId, email);
  }

  @GetMapping("isAttempted")
  ResponseEntity<?> isAttempted(@RequestParam long surveyId, @RequestParam String email) {
    if (surveyService.isAttempted(surveyId, email)) {
      return new ResponseEntity<>(new ErrorDto(CommonConstants.ALREADY_ATTEMPTED_SURVEY),
          HttpStatus.METHOD_NOT_ALLOWED);
    }
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
