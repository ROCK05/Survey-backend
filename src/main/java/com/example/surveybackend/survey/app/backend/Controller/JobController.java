package com.example.surveybackend.survey.app.backend.Controller;

import com.example.surveybackend.survey.app.backend.Dtos.NaukriDtos.NaukriRegisterDto;;
import com.example.surveybackend.survey.app.backend.Dtos.NaukriDtos.SurveyJobDto;
import com.example.surveybackend.survey.app.backend.Services.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class JobController {

    @Autowired
    JobService jobService;
    @GetMapping("/checkNaukriUser")
    boolean checkNaukriUser(@RequestParam String email)
    {
        return jobService.checkNaukriUser(email);
    }

    @PostMapping("/naukriRegistration")
    void naukriRegistration(@RequestBody NaukriRegisterDto naukriRegisterDto)
    {
        jobService.naukriRegistration(naukriRegisterDto);
    }

    @PostMapping("/postJob")
    void postJob(@RequestBody SurveyJobDto surveyJobDto)
    {
        jobService.naukriJobPost(surveyJobDto);
    }

}
