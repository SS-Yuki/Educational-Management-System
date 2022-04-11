package com.example.lab3_behind.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "majors")
public class Major {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "major_name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "school_name")
    private School school;

    @Lob
    @Column(name = "introduction", columnDefinition="TEXT")
    private String introduction;
}
