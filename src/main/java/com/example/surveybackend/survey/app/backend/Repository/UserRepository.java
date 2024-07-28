package com.example.surveybackend.survey.app.backend.Repository;

import com.example.surveybackend.survey.app.backend.Entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {
    UserEntity findByEmail(String email);
    List<UserEntity> findAll();
    UserEntity findById(long id);
    List<UserEntity> findByEmailAndRoleIn(String email, List<String> roles);
}
