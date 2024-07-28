package com.example.surveybackend.survey.app.backend.Security;

import com.example.surveybackend.survey.app.backend.Entities.UserEntity;
import com.example.surveybackend.survey.app.backend.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {

        UserEntity user = repo.findByEmail(mail);
        if(user==null)
            throw new UsernameNotFoundException("User 404");

        return new UserPrincipal(user);
    }

}
