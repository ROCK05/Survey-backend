package com.example.surveybackend.survey.app.backend.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SurveyAndQuestionMerged {

    private long surveyId;
    private String surveyName;
    private String surveyDescription;
    private String surveyorMail;
    private boolean general;
    private boolean anonymous;
    private boolean visibility;
    private Date startDate;
    private Date endDate;
    private long timeLimit;
    private int totalPoints;

    private List<Map<String,Object>> questions;
}
