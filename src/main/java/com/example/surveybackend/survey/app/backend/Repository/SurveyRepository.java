package com.example.surveybackend.survey.app.backend.Repository;

import com.example.surveybackend.survey.app.backend.Entities.SurveyEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface SurveyRepository
    extends JpaRepository<SurveyEntity, Long>, PagingAndSortingRepository<SurveyEntity, Long> {
  List<SurveyEntity> findByIsGeneralAndVisibility(boolean isGeneral, boolean visibility);

  Page<SurveyEntity> findByIsGeneralAndVisibility(boolean isGeneral, boolean visibility,
      Pageable pageable);

  List<SurveyEntity> findBySurveyorId(Long surveyorId);

  SurveyEntity findById(long id);

  SurveyEntity findByIdAndVisibility(long id, boolean visibility);

  SurveyEntity findByIdAndSurveyorId(long id, Long surveyorId);

  List<SurveyEntity> findByStartDate(Date todayDate);

  List<SurveyEntity> findByEndDate(Date endDate);

}
