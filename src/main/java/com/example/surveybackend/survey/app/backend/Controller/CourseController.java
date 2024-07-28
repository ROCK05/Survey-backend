package com.example.surveybackend.survey.app.backend.Controller;

import com.example.surveybackend.survey.app.backend.Dtos.CourseDto.FeedbackSurveyDto;
import com.example.surveybackend.survey.app.backend.Services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*")
@RequestMapping("/coursera")
public class CourseController {

    @Autowired
    CourseService courseService;
    @GetMapping("/checkUser")
    boolean checkUser(@RequestParam String email)
    {
        return courseService.checkUser(email);
    }

    @PostMapping("/postFeedback")
    long postFeedBack(@RequestBody FeedbackSurveyDto feedbackSurveyDto)
    {
        System.out.println(feedbackSurveyDto.getCourseName());
        return courseService.createSurveyFeedBack(feedbackSurveyDto);
    }
}
