package com.example.lab3_behind.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "teacher")

public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "job_number", length = 36, nullable = false)
    private String job_number;
    @Column(name = "name", length = 256, nullable = false)
    private String name;
    @Column(name = "ID_num", length = 256, nullable = false)
    private String ID_num;
    @Column(name = "phone_num", length = 256, nullable = true)
    private String phone_num;
    @Column(name = "email", length = 256, nullable = true)
    private String email;
}
