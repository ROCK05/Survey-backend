package com.example.surveybackend.survey.app.backend.Dtos.SurveyDtos;

import com.example.surveybackend.survey.app.backend.Entities.SurveyEntity;
import com.example.surveybackend.survey.app.backend.Model.QuestionsModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SurveyDto {

    private String surveyorEmail;
    private String surveyName;
    private String surveyDescription;
    private boolean anonymous;
    private boolean isGeneral;
    private boolean multipleAttempts;
    private boolean visibility;
    private Date startDate;
    private Date endDate;
    private long timeLimit;
    private int totalPoints;
    private QuestionsModel[] questions;
    public SurveyEntity toSurveyEntity(long surveyorId)
    {
        return new SurveyEntity(0,surveyorId,this.surveyName,this.surveyDescription,this.anonymous,this.isGeneral,this.multipleAttempts,
                this.visibility,this.startDate,this.endDate,this.timeLimit,this.totalPoints);
    }

}
