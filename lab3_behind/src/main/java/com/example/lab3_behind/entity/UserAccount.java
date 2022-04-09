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
@Table(name = "userAccount")
public class UserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "account", length = 36, nullable = false)
    private String numberid;
    @Column(name = "account", length = 256, nullable = false)
    private String password;
    @Column(name = "role", length = 256, nullable = false)
    private String role;
}
