package com.example.lab3_behind.service.impl;

import com.example.lab3_behind.common.Global;
import com.example.lab3_behind.common.forDomain.AuthorityName;
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
    public Integer getPresentCourseSelectingRound() {
        Authority round = authorityRepository.findByAuthorityName(AuthorityName.CourseSelectingRound);
        if (round == null){
            Authority courseSelectingAuthority = new Authority(null, AuthorityName.CourseSelecting, "0");
            authorityRepository.save(courseSelectingAuthority);
            return 0;
        }
        switch (round.getAuthorityValue()){
            case "1": return 1;
            case "2": return 2;
            case "0":
            default: return 0;
        }
    }

    @Override
    public void courseSelectingStart() {

    }

    @Override
    public void courseSelectingEnd() {
        Authority round = authorityRepository.findByAuthorityName(AuthorityName.CourseSelectingRound);
        if (round == null){
            Authority courseSelectingAuthority = new Authority(null, AuthorityName.CourseSelecting, "0");
            authorityRepository.save(courseSelectingAuthority);
            return;
        }
        round.setAuthorityValue(Global.NOT_IN_COURSE_SELECTING_ROUND);
        authorityRepository.save(round);
    }

    @Override
    public void toNextCourseSelectingRound() throws Exception {
        Authority round = authorityRepository.findByAuthorityName(AuthorityName.CourseSelectingRound);
        if (round == null){
            Authority courseSelectingAuthority = new Authority(null, AuthorityName.CourseSelecting, "0");
            authorityRepository.save(courseSelectingAuthority);
            return;
        }
        String authorityValue = round.getAuthorityValue();
        if(authorityValue.equals(Global.NOT_IN_COURSE_SELECTING_ROUND)){
            throw new Exception("还未开始选课轮次");
        }
        if(authorityValue.equals(Global.LAST_COURSE_SELECTING_ROUND)){
            throw new Exception("已无下一轮选课");
        }
        switch (authorityValue) {
            case "1": round.setAuthorityValue("2"); break;
            //...
            default:
        }
    }

    @Override
    public void changeCourseSelectingAuthority(boolean status) throws Exception {
        Authority authority = authorityRepository.findByAuthorityName(AuthorityName.CourseSelecting);
        if (authority == null){
            Authority courseSelectingAuthority = new Authority(null, AuthorityName.CourseSelecting, "false");
            authorityRepository.save(courseSelectingAuthority);
            return;
        }
        if(status){
            Authority round = authorityRepository.findByAuthorityName(AuthorityName.CourseSelectingRound);
            if(round.getAuthorityValue().equals(Global.NOT_IN_COURSE_SELECTING_ROUND)){
                throw new Exception("当前不是选课阶段");
            }
            authority.setAuthorityValue("true");
            authorityRepository.save(authority);
            return;
        }
        authority.setAuthorityValue("false");
        authorityRepository.save(authority);
    }
}
