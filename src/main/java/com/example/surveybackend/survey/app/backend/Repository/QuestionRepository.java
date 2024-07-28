package com.example.surveybackend.survey.app.backend.Repository;

import com.example.surveybackend.survey.app.backend.MongoDocuments.QuestionsDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends MongoRepository<QuestionsDocument,Long> {

    QuestionsDocument findBySurveyId(long id);
}
