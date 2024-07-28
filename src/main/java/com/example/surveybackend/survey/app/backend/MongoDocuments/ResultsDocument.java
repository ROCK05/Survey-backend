package com.example.surveybackend.survey.app.backend.MongoDocuments;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import org.springframework.data.annotation.Id;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "results")
public class ResultsDocument {

    @Id
    private long surveyId;
    private long totalResponses;
    private List<Integer> earnedPoints;
    private Map<String,Object> surveyResults;

    public ResultsDocument(long surveyId, int i) {
        this.surveyId = surveyId;
        this.totalResponses = i;
    }
}
