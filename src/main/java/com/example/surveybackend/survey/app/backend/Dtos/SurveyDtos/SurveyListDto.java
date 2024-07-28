package com.example.surveybackend.survey.app.backend.Dtos.SurveyDtos;

import com.example.surveybackend.survey.app.backend.Entities.SurveyEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SurveyListDto {

    private long surveyId;
    private String surveyName;
    private String surveyDescription;
    private boolean multipleAttempts;

    public SurveyListDto(SurveyEntity surveyEntity)
    {
        this.surveyId = surveyEntity.getId();
        this.surveyName = surveyEntity.getSurveyName();
        this.surveyDescription = surveyEntity.getSurveyDescription();
        this.multipleAttempts = surveyEntity.isMultipleAttempts();
    }

}
