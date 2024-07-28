package com.example.surveybackend.survey.app.backend.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "response_queue_table")
public class ResponseQueueEntity {

    @Id
    @Column(name ="id")
    private long id;

    @Column(name = "response_id")
    private String responseId;
}
