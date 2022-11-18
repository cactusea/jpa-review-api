package com.jpa.reviewsystem.service;

import com.jpa.reviewsystem.dto.req.ReviewSaveReqDto;
import com.jpa.reviewsystem.dto.res.ReviewResDto;
import com.jpa.reviewsystem.dto.req.ReviewUpdateReqDto;
import com.jpa.reviewsystem.entity.Review;
import com.jpa.reviewsystem.repository.LectureRepository;
import com.jpa.reviewsystem.repository.ReviewRepository;
import com.jpa.reviewsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final LectureRepository lectureRepository;

    @Transactional(rollbackOn = Exception.class)
    public ReviewResDto getReview(Long reviewId) {

        Review res = null;
        try {
            res = reviewRepository.findById(reviewId).orElseThrow();
        }catch (NoSuchElementException e){
            log.error("존재하지 않는 리뷰입니다. :: {}", e.getMessage());
            e.printStackTrace();
            return null;
        }
        ReviewResDto reviewResDto = new ReviewResDto().toDto(res);
        return reviewResDto;
    }

    @Transactional(rollbackOn = Exception.class)
    public Page<ReviewResDto> getReviewPage(Long lectureId, Pageable pageable){
        try {
            return reviewRepository.findAllByLectureId(lectureId, pageable).map(p ->
                    new ReviewResDto().toDto(p)
            );
        } catch (PropertyReferenceException e){
            log.error("PropertyReferenceException :: {}", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Transactional(rollbackOn = Exception.class)
    public Long saveReview(ReviewSaveReqDto reviewReq) {

        Review review = reviewReq.toEntity();

        try {
            review.setUser(userRepository.findById(reviewReq.getUserId()).orElseThrow());
            review.setLecture(lectureRepository.findById(reviewReq.getLectureId()).orElseThrow());
        } catch (NoSuchElementException e){
            log.error("객체가 존재하지 않습니다. :: {}", e.getMessage());
            e.printStackTrace();
            return null;
        }
        Long id = reviewRepository.save(review).getId();
        return id;
    }

    @Transactional(rollbackOn = Exception.class)
    public Long updateReview(ReviewUpdateReqDto reviewReq, Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(
                ()-> new NoSuchElementException("존재하지 않는 리뷰입니다. :: "+reviewId));
        Long id = review.update(reviewReq.toEntity());
        return id;
    }

    @Transactional(rollbackOn = Exception.class)
    public void addLikeReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(
                ()-> new NoSuchElementException("존재하지 않는 리뷰입니다. :: "+reviewId)
        );
        review.addLike();
    }

    @Transactional(rollbackOn = Exception.class)
    public Boolean deleteReview(Long reviewId) {
        reviewRepository.delete(
                reviewRepository.findById(reviewId).orElseThrow(
                ()-> new NoSuchElementException("존재하지 않는 리뷰입니다. ::"+reviewId)
        ));
        return true;
    }

}
