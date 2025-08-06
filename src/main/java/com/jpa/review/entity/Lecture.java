package com.jpa.review.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "lectures")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Lecture {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lecture_id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "difficulty")
    private LectureDifficulty difficulty;

    @Column(name = "instructor")
    private String instructor;

    @Column(name = "period")
    private String period;

    @OneToMany(mappedBy = "lecture", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    @Builder
    public Lecture(Long id, String title, LectureDifficulty difficulty, String instructor, String period) {
        this.id = id;
        this.title = title;
        this.difficulty = difficulty;
        this.instructor = instructor;
        this.period = period;
    }

    public void addReview(Review review) {
        this.reviews.add(review);
        review.setLecture(this);
    }

    public enum LectureDifficulty {
        BEGINNER("초급"),
        INTERMEDIATE("중급"),
        ADVANCED("고급");

        private final String displayName;

        LectureDifficulty(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }
}
