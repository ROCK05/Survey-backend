package com.example.surveybackend.survey.app.backend.Controller;

import com.example.surveybackend.survey.app.backend.Dtos.ErrorDto;
import com.example.surveybackend.survey.app.backend.Dtos.UserDtos.SignUpDto;
import com.example.surveybackend.survey.app.backend.Dtos.UserDtos.UserDto;
import com.example.surveybackend.survey.app.backend.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/signup")
    ResponseEntity<?> signUpUser(@RequestBody(required = true) SignUpDto signUpDto) {
        if (userService.userSignUp(signUpDto))
            return new ResponseEntity<>(signUpDto, HttpStatus.OK);
        return new ResponseEntity<>(new ErrorDto("User Already Exist!"), HttpStatus.CONFLICT);
    }

    @PostMapping("/login")
    ResponseEntity<?> getLoginUser(@RequestParam String email, @RequestParam String password) {
        return userService.userLogin(email, password);
    }

    @PutMapping("/updateUser")
    ResponseEntity<?> updateUser(@RequestBody UserDto userDto) {
        return new ResponseEntity<>(userService.updateUser(userDto), HttpStatus.OK);
    }
}




