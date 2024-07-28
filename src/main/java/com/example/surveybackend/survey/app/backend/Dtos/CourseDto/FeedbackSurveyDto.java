package com.example.surveybackend.survey.app.backend.Dtos.CourseDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackSurveyDto {

    private String courseName;
    private String courseDescription;
    private String instructorEmail;
    private CourseQuestionModel[] questions;
}

