package com.example.surveybackend.survey.app.backend.Controller;

import com.example.surveybackend.survey.app.backend.CommonConstants;
import com.example.surveybackend.survey.app.backend.Dtos.ErrorDto;
import com.example.surveybackend.survey.app.backend.Services.AdminService;
import com.example.surveybackend.survey.app.backend.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/admin")
public class AdminController {

  @Autowired
  AdminService adminService;

  @Autowired
  UserService userService;

  @GetMapping("/{email}/getUserList")
  ResponseEntity<?> getUserList(@PathVariable("email") String email) {
      if (!userService.validateUserForRoles(email,
          Collections.singletonList(CommonConstants.ADMIN))) {
          return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
      }
    return new ResponseEntity<>(adminService.getUserList(), HttpStatus.OK);
  }

  @GetMapping("/{email}/getAllSurveys")
  ResponseEntity<?> getAllSurveys(@PathVariable("email") String email) {
      if (!userService.validateUserForRoles(email,
          Collections.singletonList(CommonConstants.ADMIN))) {
          return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
      }
    return new ResponseEntity<>(adminService.getAllSurveyList(), HttpStatus.OK);
  }

  @DeleteMapping("/{email}/deleteResult")
  ResponseEntity<?> removeResult(@RequestParam long surveyId, @PathVariable("email") String email) {
      if (!userService.validateUserForRoles(email,
          Collections.singletonList(CommonConstants.ADMIN))) {
          return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
      }
      if (adminService.removeResult(surveyId)) {
          return new ResponseEntity<>(HttpStatus.OK);
      }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @DeleteMapping("/{email}/deleteSurvey")
  ResponseEntity<?> removeSurvey(@RequestParam long surveyId, @PathVariable("email") String email) {
      if (!userService.validateUserForRoles(email,
          Collections.singletonList(CommonConstants.ADMIN))) {
          return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
      }
      if (adminService.removeSurvey(surveyId)) {
          return new ResponseEntity<>(HttpStatus.OK);
      }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }

  @DeleteMapping("/{email}/deleteUser")
  ResponseEntity<?> removeUser(@RequestParam String deleteUserWithEmail,
      @PathVariable("email") String email) {
      if (!userService.validateUserForRoles(email,
          Collections.singletonList(CommonConstants.ADMIN))) {
          return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
      }
      if (adminService.removeUser(deleteUserWithEmail)) {
          return new ResponseEntity<>(HttpStatus.OK);
      }
    return new ResponseEntity<>(new ErrorDto("Admin can not be removed!"), HttpStatus.UNAUTHORIZED);
  }


  @DeleteMapping("/{email}/deleteResponses")
  ResponseEntity<?> removeResponses(@RequestParam long surveyId,
      @PathVariable("email") String email) {
      if (!userService.validateUserForRoles(email,
          Collections.singletonList(CommonConstants.ADMIN))) {
          return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
      }
      if (adminService.removeResponse(surveyId)) {
          return new ResponseEntity<>(HttpStatus.OK);
      }
    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
  }


}
