package com.example.lab3_behind.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "teaching_building")
public class TeachingBuilding {
    @Id
    @Column(name = "name")
    String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "teachingBuilding")
    List<Classroom> classrooms;
}
