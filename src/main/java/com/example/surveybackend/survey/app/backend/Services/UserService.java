package com.example.surveybackend.survey.app.backend.Services;

import com.example.surveybackend.survey.app.backend.Dtos.ErrorDto;
import com.example.surveybackend.survey.app.backend.Dtos.UserDtos.SignUpDto;
import com.example.surveybackend.survey.app.backend.Dtos.UserDtos.UserDto;
import com.example.surveybackend.survey.app.backend.Entities.UserEntity;
import com.example.surveybackend.survey.app.backend.Repository.UserRepository;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;
    public boolean userSignUp(SignUpDto signUpDto)
    {
        if(userRepository.findByEmail(signUpDto.getEmail()) != null)
            return false;
        UserEntity user = signUpDto.toSignUpEntity();
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        userRepository.save(user);
        return true;
    }

    public ResponseEntity<?> userLogin(String email, String password) {
        UserEntity user = userRepository.findByEmail(email);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            return new ResponseEntity<>(new ErrorDto("Wrong password!"), HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(user.toUserDto(), HttpStatus.OK);
    }

    public UserDto updateUser(UserDto userDto) {
        UserEntity user = userRepository.findByEmail(userDto.getEmail());
        if(user != null) {
            user.setName(userDto.getName());
            user.setGender(userDto.getGender());
            userRepository.save(user);
        }
        return userDto;
    }

    public Boolean validateUserForRoles(String email, List<String> roles) {
        List<UserEntity> userEntities = userRepository.findByEmailAndRoleIn(email, roles);
        return userEntities.size() != NumberUtils.INTEGER_ZERO;
    }

}
