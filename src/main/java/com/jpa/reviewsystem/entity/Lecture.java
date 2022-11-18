package com.jpa.reviewsystem.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Lecture {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lecture_id")
    private Long id;

    @OneToMany(mappedBy = "lecture")
    private List<Review> reviewList = new ArrayList<>();

    @Column(name = "title")
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "difficulty")
    private LectureDifficulty difficulty;

//    @ManyToOne
//    @JoinColumn(name = "instructor_id")
//    private Instructor instructor;
    private String instructor;

    @Column(name = "lecture_period")
    private String period;

    public void addReview(Review review) {
        this.reviewList.add(review);
        review.setLecture(this);
    }

    public enum LectureDifficulty{
        초급, 중급, 고급
    }
}
