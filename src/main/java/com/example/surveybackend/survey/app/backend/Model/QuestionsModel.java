package com.example.surveybackend.survey.app.backend.Model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionsModel {

    private String question;
    private String type;
    private Boolean required;
    private String[] options;
    private int points;

}
