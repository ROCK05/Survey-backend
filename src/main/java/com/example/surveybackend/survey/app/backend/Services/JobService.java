package com.example.surveybackend.survey.app.backend.Services;

import com.example.surveybackend.survey.app.backend.Dtos.NaukriDtos.NaukriRegisterDto;
import com.example.surveybackend.survey.app.backend.Dtos.NaukriDtos.SurveyJobDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

@Service
public class JobService {

    String url = "http://172.16.29.143:8080";
   public boolean checkNaukriUser(String email) {
       RestTemplate restTemplate = new RestTemplate();
       return Boolean.TRUE.equals(restTemplate.getForObject(url + "/survey/checkRecruiter?recruiterEmail=" + email, boolean.class));
   }

   public void naukriRegistration(NaukriRegisterDto naukriRegisterDto)
   {
       RestTemplate restTemplate = new RestTemplate();
       ResponseEntity<String> response =
               restTemplate.postForEntity(url+"/survey/surveyRecruiterSignup", naukriRegisterDto, String.class);
   }

   public void naukriJobPost(SurveyJobDto surveyJobDto)
   {
       RestTemplate restTemplate = new RestTemplate();
       ResponseEntity<String> response =
               restTemplate.postForEntity(url+"/survey/surveyPostJob", surveyJobDto, String.class);
   }


}
