package com.example.surveybackend.survey.app.backend.Repository;

import com.example.surveybackend.survey.app.backend.Entities.CourseSurveyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<CourseSurveyEntity,Long> {

    CourseSurveyEntity findBySurveyId(long surveyId);

}
