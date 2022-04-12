package com.example.lab3_behind.domain;

import com.example.lab3_behind.common.TeacherStatus;
import com.example.lab3_behind.domain.dto.UserEnteringData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "teachers")
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "job_number", length = 36, nullable = false)
    private String jobNumber;

    @Column(name = "name", length = 256, nullable = false)
    private String name;

    @Column(name = "ID_num", length = 256, nullable = false)
    private String idNum;

    @Column(name = "phone_num", length = 256, nullable = true)
    private String phoneNum;

    @Column(name = "email", length = 256, nullable = true)
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account")
    private UserAccount userAccount;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "course_id")
    private List<Course> courses;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "course_applying_id")
    private List<CourseApplying> coursesApplying;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TeacherStatus status;

    @Column(name = "school")
    private String school;

    @Column(name = "major")
    private String major;

    public Teacher(UserEnteringData user){
        this.jobNumber = user.getNumber();
        this.name = user.getName();
        this.idNum = user.getIdNum();
        this.phoneNum = user.getPhoneNum();
        this.email = user.getEmail();
        this.school = user.getSchool();
        this.major = user.getMajor();
        this.status = TeacherStatus.Normal;
        UserAccount account = new UserAccount();
        account.setAccount(user.getNumber());
        account.setPassword("fDu" + this.jobNumber);
        account.setRole("teacher");
        account.setPermission("true");
        this.userAccount = account;
    }
}
