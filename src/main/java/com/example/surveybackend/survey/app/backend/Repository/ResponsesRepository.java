package com.example.surveybackend.survey.app.backend.Repository;

import com.example.surveybackend.survey.app.backend.MongoDocuments.ResponsesDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("ResponseRepository")
public interface ResponsesRepository extends MongoRepository<ResponsesDocument,String> {
    Optional<ResponsesDocument> findById(String id);

    List<ResponsesDocument> findBySurveyId(long id);

    ResponsesDocument findByCandidateIdAndSurveyId(Long id,long surveyId);

    void deleteBySurveyId(long id);
}