package com.example.surveybackend.survey.app.backend.Repository;

import com.example.surveybackend.survey.app.backend.Entities.ResponseQueueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("ResponseQueueTableRepository")
public interface ResponseQueueTableRepository extends JpaRepository<ResponseQueueEntity,Long> {

    List<ResponseQueueEntity> findAll();
}
