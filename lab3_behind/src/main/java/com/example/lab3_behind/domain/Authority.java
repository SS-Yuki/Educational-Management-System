package com.example.lab3_behind.domain;

import com.example.lab3_behind.common.AuthorityName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "authority")
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "authority_name", unique = true)
    private AuthorityName authorityName;

    @Column(name = "authority_value")
    private String authorityValue;
}
