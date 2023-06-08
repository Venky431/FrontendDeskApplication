package com.example.frontdesk.entities;

import lombok.Data;

import javax.persistence.*;


@Entity
@Data
@Table(name="courses")
public class Courses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer courseId;
    private String courseName;
}
