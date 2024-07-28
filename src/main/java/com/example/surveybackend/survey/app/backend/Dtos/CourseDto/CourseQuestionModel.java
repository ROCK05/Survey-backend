package com.example.surveybackend.survey.app.backend.Dtos.CourseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseQuestionModel {

    private String question;
    private String[] options;
}
