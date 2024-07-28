package com.example.surveybackend.survey.app.backend.Dtos.ResponseDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SurveyResponsesDto {
    private String candidateEmail;
    private String candidateName;
    private long surveyId;
    private int earnedPoints;
    private Map<String,Object> surveyAnswers;
}
