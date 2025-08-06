package com.jpa.review.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "reviews",
        indexes = {
                @Index(name = "idx_user_id", columnList = "user_id"),
                @Index(name = "idx_lecture_id", columnList = "lecture_id"),
                @Index(name = "idx_lecture_id_rating", columnList = "lecture_id, rating"),
                @Index(name = "idx_created_date", columnList = "created_date")
        })
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Review {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lecture_id", nullable = false)
    private Lecture lecture;

    @Column(name = "rating", nullable = false)
    @Min(1) @Max(5)
    private Integer rating;

    @Column(name = "likes_count", nullable = false)
    private Integer likesCount;

    @Column(name = "content", nullable = false)
    @Size(max = 1000)
    private String content;

    @CreatedDate
    @Column(name = "created_date", updatable = false, nullable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(name = "modified_date", nullable = false)
    private LocalDateTime modifiedDate;

    // 좋아요 증가(+1)
    public void addLike() {
        this.likesCount++;
    }

    // 리뷰 수정
    public void update(Integer rating, String content) {
        this.rating = rating;
        this.content = content;
    }

    // 연관관계 편의 메서드
    public void setUser(User user) {
        this.user = user;
    }

    public void setLecture(Lecture lecture) {
        this.lecture = lecture;
    }
}
