package com.example.surveybackend.survey.app.backend.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Data
@Table(name = "survey")
@AllArgsConstructor
@NoArgsConstructor
public class SurveyEntity {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="surveyor_id")
    private Long surveyorId;

    @Column(name="survey_name")
    private String surveyName;

    @Column(name="survey_description")
    private String surveyDescription;

    @Column(name="anonymous")
    private boolean anonymous;
    @Column(name="is_general")
    private boolean isGeneral;

    @Column(name="multiple_attempts")
    private boolean multipleAttempts;

    @Column(name="visibility")
    private boolean visibility;

    @Column(name="start_date")
    private Date startDate;

    @Column(name="end_date")
    private Date endDate;

    @Column(name="time_limit")
    private long timeLimit;

    @Column(name="total_points")
    private int totalPoints;

    public SurveyEntity(int i, Long surveyorId, String courseName, String courseDescription, boolean anonymous, boolean isGeneral, boolean multipleAttempts, boolean visibility) {

        this.id = i;
        this.surveyorId = surveyorId;
        this.surveyName = courseName;
        this.surveyDescription = courseDescription;
        this.anonymous = anonymous;
        this.isGeneral = isGeneral;
        this.multipleAttempts = multipleAttempts;
        this.visibility = visibility;
    }

}
