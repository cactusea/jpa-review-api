package com.jpa.review.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jpa.review.entity.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    
    /**
     * 특정 강의의 리뷰들을 페이징하여 조회
     */
    Page<Review> findAllByLectureId(Long lectureId, Pageable pageable);
    
    /**
     * 특정 사용자의 리뷰들을 페이징하여 조회
     */
    Page<Review> findAllByUserId(Long userId, Pageable pageable);
    
    /**
     * 특정 강의의 평균 평점 조회
     */
    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.lecture.id = :lectureId")
    Double findAverageRatingByLectureId(@Param("lectureId") Long lectureId);
    
    /**
     * 특정 강의의 리뷰 개수 조회
     */
    @Query("SELECT COUNT(r) FROM Review r WHERE r.lecture.id = :lectureId")
    Long countByLectureId(@Param("lectureId") Long lectureId);
}
