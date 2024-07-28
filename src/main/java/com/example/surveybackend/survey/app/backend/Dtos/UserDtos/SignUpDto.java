package com.example.surveybackend.survey.app.backend.Dtos.UserDtos;

import com.example.surveybackend.survey.app.backend.Entities.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDto {
    private String email;
    private String password;
    private String role;

    public UserEntity toSignUpEntity()
    {
        return new UserEntity(null,null,this.email,this.password,null,this.role);
    }

}
