package com.example.surveybackend.survey.app.backend.Services;

import com.example.surveybackend.survey.app.backend.Model.QuestionsModel;
import com.example.surveybackend.survey.app.backend.MongoDocuments.QuestionsDocument;
import com.example.surveybackend.survey.app.backend.MongoDocuments.ResultsDocument;
import com.example.surveybackend.survey.app.backend.Repository.QuestionRepository;
import com.example.surveybackend.survey.app.backend.Repository.ResultRepository;
import com.example.surveybackend.survey.app.backend.Utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    ResultRepository resultRepository;

    @Autowired
    Utils utils;

    public void addQuestion(long surveyId, QuestionsModel[] questions)
    {
        QuestionsDocument questionsDocument = new QuestionsDocument();
        questionsDocument.setSurveyId(surveyId);
        questionsDocument.setQuestions(utils.formatQuestion(questions));
        questionRepository.save(questionsDocument);

        //Create Result
        ResultsDocument resultsDocument = utils.createResult(surveyId, questions);
        resultRepository.save(resultsDocument);
    }

}
