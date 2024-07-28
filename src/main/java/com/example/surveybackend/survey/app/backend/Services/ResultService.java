package com.example.surveybackend.survey.app.backend.Services;

import com.example.surveybackend.survey.app.backend.Entities.ResponseQueueEntity;
import com.example.surveybackend.survey.app.backend.MongoDocuments.ResponsesDocument;
import com.example.surveybackend.survey.app.backend.MongoDocuments.ResultsDocument;
import com.example.surveybackend.survey.app.backend.Repository.ResponseQueueTableRepository;
import com.example.surveybackend.survey.app.backend.Repository.ResponsesRepository;
import com.example.surveybackend.survey.app.backend.Repository.ResultRepository;
import com.example.surveybackend.survey.app.backend.Utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@EnableScheduling
public class ResultService {

    @Autowired
    ResultRepository resultRepository;
    @Autowired
    ResponsesRepository responseRepository;

    @Autowired
    ResponseQueueTableRepository responseQueueTableRepository;

    @Autowired
    Utils utils;

    void updateResult(ResponsesDocument responsesDocument)
    {
        ResultsDocument resultsDocument = resultRepository.findBySurveyId(responsesDocument.getSurveyId());
        if(resultsDocument != null) {
            resultsDocument.setTotalResponses(resultsDocument.getTotalResponses() + 1);
            for (Map.Entry<String, Object> entry : resultsDocument.getSurveyResults().entrySet()) {
                Object value = entry.getValue();
                if (value instanceof Integer && responsesDocument.getSurveyAnswers().containsKey(entry.getKey())) {
                    resultsDocument.getSurveyResults().put(entry.getKey(), (Integer) value + 1);
                } else if (responsesDocument.getSurveyAnswers().containsKey(entry.getKey())) {
                    ArrayList selectedOption = (ArrayList) responsesDocument.getSurveyAnswers().get(entry.getKey());
                    List<Object> values = (List<Object>) value;
                    utils.updateOptionsResponse(selectedOption, values, resultsDocument, entry);
                }
            }
            resultRepository.save(resultsDocument);
        }

    }

    @Scheduled(fixedRate = 1000)
    void updateResults() {
        List<ResponseQueueEntity> responseQueueEntities = responseQueueTableRepository.findAll();
        if (!responseQueueEntities.isEmpty()) {
            Optional<ResponsesDocument> responsesDocument = responseRepository.findById(responseQueueEntities.get(0).getResponseId());
            responsesDocument.ifPresent(this::updateResult);
            responseQueueTableRepository.delete(responseQueueEntities.get(0));
        }
    }

    public ResultsDocument getResult(long surveyId)
    {
        return resultRepository.findBySurveyId(surveyId);
    }

}
