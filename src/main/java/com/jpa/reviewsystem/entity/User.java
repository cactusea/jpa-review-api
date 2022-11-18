package com.jpa.reviewsystem.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "USER")
@Getter @Setter
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    public Long id;

    @Column(name = "user_name")
    @Size(max=30)
    public String name;

    @Column(name = "thumbnail_url")
    @Size(max=50)
    public String thumbnailUrl;
}
