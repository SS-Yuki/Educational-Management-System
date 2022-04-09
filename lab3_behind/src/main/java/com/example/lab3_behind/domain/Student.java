package com.example.lab3_behind.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "stu_number", length = 36, nullable = false)
    private String stu_number;
    @Column(name = "name", length = 256, nullable = false)
    private String name;
    @Column(name = "ID_num", length = 256, nullable = false)
    private String ID_num;
    @Column(name = "phone_num", length = 256, nullable = true)
    private String phone_num;
    @Column(name = "email", length = 256, nullable = true)
    private String email;
    @OneToOne
    @JoinColumn(name = "account")
    private UserAccount userAccount;
}
