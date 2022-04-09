package com.example.lab3_behind.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
    private String account;
    @Column(name = "password", length = 256, nullable = false)
    private String password;
    @Column(name = "role", length = 256, nullable = false)
    private String role;
}
