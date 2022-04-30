package com.example.lab3_behind.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "classrooms")
public class Classroom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "teaching_building")
    private TeachingBuilding teachingBuilding;

    @Column(name = "capacity")
    private Integer capacity;

    @Lob
    @Column(name = "schedule", columnDefinition="TEXT")
    private String schedule;
    /*格式说明：
    id-id-id-id-id-id-id\n
    id-id-id-id-id-id-id\n
    id-id-id-id-id-id-id\n
    ......
    每列依次对于星期一、二、三...
     */

}
