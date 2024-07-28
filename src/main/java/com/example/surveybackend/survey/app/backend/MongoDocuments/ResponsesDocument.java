package com.example.surveybackend.survey.app.backend.MongoDocuments;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.persistence.Id;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "responses")
public class ResponsesDocument {

    @Id
    private String id;
    private Long candidateId;
    private long surveyId;
    private int earnedPoints;
    private Map<String,Object> surveyAnswers;

    public ResponsesDocument(long surveyId, int earnedPoints, Map<String, Object> surveyAnswers) {
        this.surveyId=surveyId;
        this.earnedPoints=earnedPoints;
        this.surveyAnswers=surveyAnswers;
    }

}
