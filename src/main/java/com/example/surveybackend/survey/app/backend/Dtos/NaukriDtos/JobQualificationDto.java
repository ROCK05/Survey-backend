package com.example.surveybackend.survey.app.backend.Dtos.NaukriDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobQualificationDto {
    private String qualification;
    private double percentage;
}
