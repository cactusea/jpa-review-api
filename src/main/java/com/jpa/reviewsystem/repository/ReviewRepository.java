package com.jpa.reviewsystem.repository;

import com.jpa.reviewsystem.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Page<Review> findAllByLectureId(Long lectureId, Pageable pageable);

//    List<Review> findAllByLectureIdOrderBOrderByLikeDesc(Long lectureId);
//    List<Review> findAllByLectureIdOrderBOrderByCreatedDateDesc(Long lectureId);
//    List<Review> findAllByLectureIdOrderBOrderByGradeDesc(Long lectureId);
//    List<Review> findAllByLectureIdOrderBOrderByGradeAsc(Long lectureId);
}
