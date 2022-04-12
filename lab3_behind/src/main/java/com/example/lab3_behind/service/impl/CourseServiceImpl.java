package com.example.lab3_behind.service.impl;

import com.example.lab3_behind.common.CourseApplyingType;
import com.example.lab3_behind.domain.Course;
import com.example.lab3_behind.domain.CourseApplying;
import com.example.lab3_behind.domain.dto.CourseApplyingData;
import com.example.lab3_behind.repository.CourseApplyingRepository;
import com.example.lab3_behind.repository.CourseRepository;
import com.example.lab3_behind.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl implements CourseService {
    CourseRepository courseRepository;
    CourseApplyingRepository courseApplyingRepository;
    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository, CourseApplyingRepository courseApplyingRepository){
        this.courseRepository = courseRepository;
        this.courseApplyingRepository = courseApplyingRepository;
    }

//    @Override
//    public Page<Course> findAPageCourse(Integer page, Integer size, String search){}
//
//    @Override
//    public Page<CourseApplying> findAPageCourseApplying(Integer page, Integer size, String search){}

    @Override
    public CourseApplying applyToAddCourse(CourseApplyingData courseData){
        CourseApplying courseApplying = new CourseApplying((courseData));
        courseApplying.setType(CourseApplyingType.Publish);
        courseApplyingRepository.save(courseApplying);
        return courseApplying;
    }

    @Override
    public CourseApplying applyToUpdateCourse(CourseApplyingData courseData){
        CourseApplying courseApplying = new CourseApplying((courseData));
        courseApplying.setType(CourseApplyingType.Change);
        courseApplyingRepository.save(courseApplying);
        return courseApplying;
    }

    @Override
    public CourseApplying applyToDeleteCourse(CourseApplyingData courseData){
        CourseApplying courseApplying = new CourseApplying((courseData));
        courseApplying.setType(CourseApplyingType.Delete);
        courseApplyingRepository.save(courseApplying);
        return courseApplying;
    }

//    @Override
//    public Course insertCourse(CourseApplyingData courseData){}
//
//    @Override
//    public Course updateCourse(CourseApplyingData courseData){}
//
//    @Override
//    public Course deleteCourse(CourseApplyingData courseData){}
}
