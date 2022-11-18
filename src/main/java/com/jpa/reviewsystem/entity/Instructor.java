package com.jpa.reviewsystem.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Instructor {

    @Id @GeneratedValue
    @Column(name = "instructor_id")
    private Long id;

    @Column(name = "instructor_name")
    private String name;
}
