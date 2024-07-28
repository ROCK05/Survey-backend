package com.example.surveybackend.survey.app.backend.MongoDocuments;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "questions")
public class QuestionsDocument {

    private long surveyId;
    private List<Map<String,Object>> questions;

}
