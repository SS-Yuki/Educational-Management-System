package com.example.lab3_behind.domain;

import com.example.lab3_behind.common.forDomain.CourseApplyingType;
import com.example.lab3_behind.common.forDomain.CourseSelectType;
import com.example.lab3_behind.common.forDomain.SchoolYear;
import com.example.lab3_behind.common.forDomain.Semester;
import com.example.lab3_behind.domain.dto.CourseApplyingData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "courseApplying")
public class CourseApplying {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "apply_id")
    private Integer applyId;

    @Column(name = "course_id")
    private Integer courseId;

    @Column(name = "course_number", length = 36, nullable = true)
    private String courseNumber;

    @Column(name = "course_name", length = 256, nullable = true)
    private String courseName;

    @Column(name = "teacher_num", nullable = true)
    private String teacherNum;

    @Column(name = "teacher_name", nullable = true)
    private String teacherName;

    @ManyToOne
    @JoinColumn(name = "school")
    private School school;

    @ManyToOne
    @JoinColumn(name = "major")
    private Major major;

    @ManyToOne
    @JoinColumn(name = "classroom")
    private Classroom classroom;

    @Column(name = "credit_hours")
    private Integer creditHours;

    @Column(name = "credits")
    private Integer credits;

    @Column(name = "capacity")
    private Integer capacity;

    @Lob
    @Column(name = "introduction", columnDefinition="TEXT")
    private String introduction;

    @Column(name = "applicant")
    private String applicant;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private CourseApplyingType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "school_year")
    private SchoolYear schoolYear;

    @Enumerated(EnumType.STRING)
    @Column(name = "semester")
    private Semester semester;

    @Enumerated(EnumType.STRING)
    @Column(name = "course_select_type")
    private CourseSelectType courseSelectType;

    @ManyToMany
    @JoinTable(
            name = "course_optional_majors",
            joinColumns = {@JoinColumn(name = "course_id")},
            inverseJoinColumns = {@JoinColumn(name = "major_id")}
    )
    List<Major> majorsOptional;

    public CourseApplying(CourseApplyingData courseApplyingData, School school, Major major, Classroom classroom, List<Major> majorsOptional){
        this.courseId = courseApplyingData.getId();
        this.teacherName = courseApplyingData.getApplicant();
        this.applicant = courseApplyingData.getApplicant();
        this.capacity = courseApplyingData.getCapacity();
        this.courseName = courseApplyingData.getCourseName();
        this.courseNumber = courseApplyingData.getCourseNumber();
        this.creditHours = courseApplyingData.getCreditHours();
        this.credits = courseApplyingData.getCredits();
        this.major = major;
        this.school = school;
        this.teacherNum = courseApplyingData.getTeacherNum();
        this.introduction = courseApplyingData.getIntroduction();
    }
}
