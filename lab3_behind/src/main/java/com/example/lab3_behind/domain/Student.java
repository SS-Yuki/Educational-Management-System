package com.example.lab3_behind.domain;

import com.example.lab3_behind.common.StudentStatus;
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
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "stu_number", length = 36, nullable = false, unique = true)
    private String stuNumber;

    @Column(name = "name", length = 256, nullable = false)
    private String name;

    @Column(name = "id_num", length = 256, nullable = false, unique = true)
    private String idNum;

    @Column(name = "phone_num", length = 256, nullable = true)
    private String phoneNum;

    @Column(name = "email", length = 256, nullable = true)
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "account")
    private UserAccount userAccount;

    @ManyToMany
    @JoinTable(
            name = "courseSelectingRecord",
            joinColumns = {@JoinColumn(name = "student_num")},
            inverseJoinColumns = {@JoinColumn(name = "course_id")}
    )
    private List<Course> courses;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StudentStatus status;

    @ManyToOne
    @JoinColumn(name = "school")
    private School school;

    @ManyToOne
    @JoinColumn(name = "major")
    private Major major;

    public Student(UserEnteringData user, School school, Major major){
        this.stuNumber = user.getNumber();
        this.name = user.getName();
        this.idNum = user.getIdNum();
        this.phoneNum = user.getPhoneNum();
        this.email = user.getEmail();
        this.status = StudentStatus.Normal;
        this.major = major;
        this.school = school;
        UserAccount account = new UserAccount();
        account.setAccount(user.getNumber());
        account.setPassword("fDu" + this.stuNumber);
        account.setRole("student");
        account.setPermission("true");
        this.userAccount = account;
    }
}
