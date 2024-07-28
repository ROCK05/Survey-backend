package com.example.surveybackend.survey.app.backend.Dtos.ResponseDtos;

import com.example.surveybackend.survey.app.backend.MongoDocuments.ResponsesDocument;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto {

    private String userEmail;
    private long surveyId;
    private int earnedPoints;
    private Map<String,Object> surveyAnswers;

    public ResponsesDocument toDocument()
    {
        return new ResponsesDocument(surveyId,earnedPoints,surveyAnswers);
    }

    public ResponsesDocument toDocument(Long userId)
    {
        System.out.println(userId);
        return new ResponsesDocument(null,userId,this.surveyId,this.earnedPoints,this.surveyAnswers);
    }
}
