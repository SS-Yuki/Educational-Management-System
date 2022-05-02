package com.example.lab3_behind.domain;

import com.example.lab3_behind.common.Global;
import com.example.lab3_behind.common.forDomain.CourseApplyingType;
import com.example.lab3_behind.common.forDomain.CourseSelectType;
import com.example.lab3_behind.common.forDomain.SchoolYear;
import com.example.lab3_behind.common.forDomain.Semester;
import com.example.lab3_behind.domain.dto.CourseApplyingData;
import com.example.lab3_behind.utils.EnumTool;
import com.example.lab3_behind.utils.TimeTool;
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
            name = "course_applying_optional_majors",
            joinColumns = {@JoinColumn(name = "course_applying_id")},
            inverseJoinColumns = {@JoinColumn(name = "major_id")}
    )
    List<Major> majorsOptional;

    @Column(name = "class_time")
    private String classTime;

    public CourseApplying(CourseApplyingData courseApplyingData, School school, Major major, Classroom classroom, List<Major> majorsOptional, String classTime){
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
        this.classroom = classroom;
        this.schoolYear = EnumTool.transSchoolYear(courseApplyingData.getYear());
        this.semester = EnumTool.transSemester(courseApplyingData.getSemester());
        this.courseSelectType = EnumTool.transCourseSelectType(courseApplyingData.getSelectTypeString());
        this.majorsOptional = majorsOptional;
        this.classTime = classTime;
    }

    public String getClassTime(){
        String result = "";
        List<List<Integer>> time = TimeTool.makeTimeMatrix(this.classTime);
        for(int i = 0; i < Global.WEEKDAY; i ++){
            String day = "周";
            switch (i) {
                case(0): day = day + "一："; break;
                case(1): day = day + "二："; break;
                case(2): day = day + "三："; break;
                case(3): day = day + "四："; break;
                case(4): day = day + "五："; break;
                case(5): day = day + "六："; break;
                case(6): day = day + "日："; break;
            }
            boolean isAdd = false;
            int sections = time.get(0).size();
            for(int k = 0; k < sections; k++){
                if(time.get(i).get(k).equals(this.courseId)){
                    isAdd = true;
                    day = day + (k + 1) + " ";
                }
            }
            day = day + "节\n";
            if(isAdd) result = result + day;
        }
        return result;
    }
}
