package com.example.surveybackend.survey.app.backend.Utils;

import com.example.surveybackend.survey.app.backend.Dtos.SurveyDtos.SurveyListDto;
import com.example.surveybackend.survey.app.backend.Dtos.ResponseDtos.SurveyResponsesDto;
import com.example.surveybackend.survey.app.backend.Entities.SurveyEntity;
import com.example.surveybackend.survey.app.backend.Model.QuestionsModel;
import com.example.surveybackend.survey.app.backend.MongoDocuments.ResponsesDocument;
import com.example.surveybackend.survey.app.backend.MongoDocuments.ResultsDocument;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class Utils {

    public List<SurveyListDto> convertToSurveyListDto(List<SurveyEntity> compeleteSurveyList) {
        List<SurveyListDto> surveyList = new ArrayList<>();
        for (SurveyEntity survey : compeleteSurveyList) {
            surveyList.add(new SurveyListDto(survey.getId(),survey.getSurveyName(), survey.getSurveyDescription(),survey.isMultipleAttempts()));
        }
        return surveyList;
    }

    public ResultsDocument createResult(long surveyId, QuestionsModel[] questions) {
        ResultsDocument result = new ResultsDocument(surveyId, 0);
        Map<String, Object> answers = new HashMap<>();

        for (int i = 0; i < questions.length; i++) {
            String customKey = "a" + Integer.toString(i + 1);
            if (questions[i].getType().equals("text"))
                answers.put(customKey, 0);
            else {
                String[] opts = new String[questions[i].getOptions().length];
                Integer[] optionResponses = new Integer[questions[i].getOptions().length];
                for (int j = 0; j < questions[i].getOptions().length; j++) {
                    opts[j] = questions[i].getOptions()[j];
                    optionResponses[j] = 0;
                }
                answers.put("a" + Integer.toString(i + 1) + "Responses", optionResponses);
                answers.put(customKey, opts);
            }
        }
        result.setSurveyResults(answers);
        return result;
    }

    public void updateOptionsResponse(ArrayList selectedOption, List<Object> values, ResultsDocument resultsDocument, Map.Entry<String, Object> entry) {
         for(Object s :selectedOption) {
            for (int j = 0; j < values.size(); j++) {
                if (values.get(j).equals(s)) {
                    ((ArrayList) resultsDocument.getSurveyResults().get(entry.getKey() + "Responses"))
                            .set(j, ((Integer) ((ArrayList) resultsDocument.getSurveyResults().get(entry.getKey() + "Responses")).get(j)) + 1);
                }
            }
        }
    }

    public void resetResult(ResultsDocument resultsDocument)
    {
        if (resultsDocument != null) {
            resultsDocument.setTotalResponses(0);
            for (Map.Entry<String, Object> entry : resultsDocument.getSurveyResults().entrySet()) {
                Object value = entry.getValue();
                if (value instanceof Integer) {
                    resultsDocument.getSurveyResults().put(entry.getKey(), 0);
                } else if (entry.getKey().contains("Responses")) {
                    List<Object> values = (List<Object>) value;
                    for (int i = 0; i < values.size(); i++) {
                        ((ArrayList) resultsDocument.getSurveyResults().get(entry.getKey()))
                                .set(i,0);
                    }
                }
            }
        }
    }


    public List<Map<String,Object>> formatQuestion(QuestionsModel[] questions) {
        List<Map<String, Object>> questionsMap = new ArrayList<>();
        for (QuestionsModel question : questions) {
            Map<String, Object> questionMap = new HashMap<>();
            questionMap.put("question", question.getQuestion());
            questionMap.put("type", question.getType());
            if (question.getRequired()) questionMap.put("required", true);
            if (question.getPoints() != 0) questionMap.put("points", question.getPoints());
            if (question.getOptions().length > 0) {
                List<String> options = new ArrayList<>(Arrays.asList(question.getOptions()));
                questionMap.put("options", options);
            }
            questionsMap.add(questionMap);
        }
        return questionsMap;
    }

    public List<SurveyResponsesDto> getAnonymousResponses(long surveyId,List<ResponsesDocument> responsesDocuments)
    {
        List<SurveyResponsesDto> responsesList = new ArrayList<>();
        for (ResponsesDocument response : responsesDocuments) {
            responsesList.add(new SurveyResponsesDto(null,null, response.getSurveyId(),
                    response.getEarnedPoints(), response.getSurveyAnswers()));
        }
        return responsesList;
    }

}
