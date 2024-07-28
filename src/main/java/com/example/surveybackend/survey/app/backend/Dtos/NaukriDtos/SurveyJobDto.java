package com.example.surveybackend.survey.app.backend.Dtos.NaukriDtos;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.List;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SurveyJobDto {
    private String recruiterEmail;
    private String jobDesignation;
    private Date doj;
    private Date postingDate;
    private int vacancy;
    private String location;
    private String description;
    private Date expiryDate;
    private int minExperience;
    private int maxExperience;
    private double ctc;
    private String surveyLink;
    private List<String> skill;
    private List<JobQualificationDto> qualification;
}

