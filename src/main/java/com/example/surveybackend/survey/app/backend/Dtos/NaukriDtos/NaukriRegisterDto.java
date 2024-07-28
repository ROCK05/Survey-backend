package com.example.surveybackend.survey.app.backend.Dtos.NaukriDtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NaukriRegisterDto {

    private String names;
    private String gender;
    private String phone;
    private String email;
    private String password;
    private String company;
    private String aboutCompany;
    private String designation;
    private String strength;

}
