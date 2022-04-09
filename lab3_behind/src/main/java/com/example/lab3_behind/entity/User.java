package com.example.h4lab2.entity;

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
@Table(name = "test11")
//@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "numberid", length = 36, nullable = false)
    private String numberid;
    @Column(name = "name", length = 256, nullable = false)
    private String name;
    @Column(name = "password", length = 256, nullable = false)
    private String password;
    @Column(name = "idcard", length = 256, nullable = false)
    private String idcard;
    @Column(name = "phonenum", length = 256, nullable = true)
    private String phonenum;
    @Column(name = "email", length = 256, nullable = true)
    private String email;
    @Column(name = "role", length = 256, nullable = false)
    private String role;

}