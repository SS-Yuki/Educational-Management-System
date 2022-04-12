package com.example.lab3_behind.service.impl;

import com.example.lab3_behind.common.AuthorityName;
import com.example.lab3_behind.repository.AuthorityRepository;
import com.example.lab3_behind.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorityServiceImpl implements AuthorityService {
    AuthorityRepository authorityRepository;
    @Autowired
    public AuthorityServiceImpl(AuthorityRepository authorityRepository){
        this.authorityRepository = authorityRepository;
    }

    @Override
    public Boolean checkCourseSelectingAuthority(){
//        authorityRepository.findByAuthorityName()
        return true;
    }
}
