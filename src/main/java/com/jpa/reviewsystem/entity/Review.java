package com.jpa.reviewsystem.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Review {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id")
    private Lecture lecture;

    @Column(name = "grade") @Min(1) @Max(5)
    private int grade;

    @Column(name = "review_like")
    private int like;

    @Column(name = "review_context")
    @Size(max=1000)
    private String context;

    @CreatedDate
    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;

    @Builder
    public Review(Long id, int grade, int like, String context, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.grade = grade;
        this.like = like;
        this.context = context;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }

    //좋아요 증가(+1)
    public void addLike(){
        this.like = getLike()+1;
    }

    public Long update(Review review){
        this.grade = review.getGrade();
        this.like = review.getLike();
        this.context = review.getContext();

        return this.id;
    }

}
