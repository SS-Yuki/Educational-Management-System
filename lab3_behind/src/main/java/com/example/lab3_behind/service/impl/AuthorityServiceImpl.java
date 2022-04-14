package com.example.lab3_behind.service.impl;

import com.example.lab3_behind.common.AuthorityName;
import com.example.lab3_behind.domain.Authority;
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
        Authority authority = authorityRepository.findByAuthorityName(AuthorityName.CourseSelecting);
        if (authority == null){
            Authority courseSelectingAuthority = new Authority(null, AuthorityName.CourseSelecting, "false");
            authorityRepository.save(courseSelectingAuthority);
            return false;
        }
        if (authority.getAuthorityValue().equals("true")){
            return true;
        }
        return false;
    }

    @Override
    public Boolean changeCourseSelectingAuthority(boolean status){
        Authority authority = authorityRepository.findByAuthorityName(AuthorityName.CourseSelecting);
        if (authority == null){
            Authority courseSelectingAuthority = new Authority(null, AuthorityName.CourseSelecting, "false");
            authorityRepository.save(courseSelectingAuthority);
        }
        Authority courseSelectingAuthority = authorityRepository.findByAuthorityName(AuthorityName.CourseSelecting);
        if(status){
            courseSelectingAuthority.setAuthorityValue("true");
            authorityRepository.save(authority);
            return true;
        }
        courseSelectingAuthority.setAuthorityValue("false");
        authorityRepository.save(authority);
        return false;
    }
}
