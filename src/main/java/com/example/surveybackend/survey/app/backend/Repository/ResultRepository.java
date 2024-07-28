package com.example.surveybackend.survey.app.backend.Repository;

import com.example.surveybackend.survey.app.backend.MongoDocuments.ResultsDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository("ResultRepository")
public interface ResultRepository extends MongoRepository<ResultsDocument,Long> {

    ResultsDocument findBySurveyId(long surveyId);
}
