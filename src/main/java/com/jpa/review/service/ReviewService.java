package com.jpa.review.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jpa.review.dto.req.ReviewSaveReqDto;
import com.jpa.review.dto.req.ReviewUpdateReqDto;
import com.jpa.review.dto.res.ReviewResponse;
import com.jpa.review.entity.Review;
import com.jpa.review.entity.User;
import com.jpa.review.entity.Lecture;
import com.jpa.review.enums.SortType;
import com.jpa.review.repository.LectureRepository;
import com.jpa.review.repository.ReviewRepository;
import com.jpa.review.repository.UserRepository;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final LectureRepository lectureRepository;

    /**
     * 페이징과 정렬이 적용된 리뷰 목록을 조회합니다.
     */
    public ReviewResponse getReviews(SortType sortType, Pageable pageable) {
        log.debug("리뷰 목록 조회 시작 - sortType: {}, page: {}, size: {}",
                sortType, pageable.getPageNumber(), pageable.getPageSize());

        Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sortType.toSort());
        Page<Review> reviewPage = reviewRepository.findAll(sortedPageable);
        Page<ReviewResponse> responsePage = reviewPage.map(this::convertToResponse);

        log.debug("리뷰 목록 조회 완료 - 조회된 리뷰 수: {}", reviewPage.getNumberOfElements());

        return ReviewResponse.from(responsePage);
    }

    /**
     * 특정 리뷰를 ID로 조회합니다.
     */
    public ReviewResponse getReview(Long reviewId) {
        log.debug("리뷰 단일 조회 시작 - reviewId: {}", reviewId);

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> {
                    log.warn("리뷰 조회 실패 - 존재하지 않는 리뷰 ID: {}", reviewId);
                    return new RuntimeException("리뷰를 찾을 수 없습니다. ID: " + reviewId);
                });

        log.debug("리뷰 단일 조회 완료 - reviewId: {}, rating: {}", reviewId, review.getRating());
        return convertToResponse(review);
    }

    /**
     * 새 리뷰를 생성합니다.
     */
    @Transactional
    public ReviewResponse createReview(ReviewSaveReqDto reviewReq) {
        log.debug("리뷰 생성 시작 - userId: {}, lectureId: {}, rating: {}", 
                reviewReq.getUserId(), reviewReq.getLectureId(), reviewReq.getRating());

        User user = userRepository.findById(reviewReq.getUserId())
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 사용자입니다. ID: " + reviewReq.getUserId()));
        
        Lecture lecture = lectureRepository.findById(reviewReq.getLectureId())
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 강의입니다. ID: " + reviewReq.getLectureId()));

        Review review = reviewReq.toEntity();
        review.setUser(user);
        review.setLecture(lecture);

        Review savedReview = reviewRepository.save(review);
        log.debug("리뷰 생성 완료 - reviewId: {}", savedReview.getId());

        return convertToResponse(savedReview);
    }

    /**
     * 기존 리뷰를 수정합니다.
     */
    @Transactional
    public ReviewResponse updateReview(Long reviewId, ReviewUpdateReqDto reviewReq) {
        log.debug("리뷰 수정 시작 - reviewId: {}, rating: {}", reviewId, reviewReq.getRating());

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 리뷰입니다. ID: " + reviewId));

        review.update(reviewReq.getRating(), reviewReq.getContent());

        log.debug("리뷰 수정 완료 - reviewId: {}", reviewId);
        return convertToResponse(review);
    }

    /**
     * 리뷰 좋아요를 증가시킵니다.
     */
    @Transactional
    public ReviewResponse increaseLikes(Long reviewId) {
        log.debug("리뷰 좋아요 증가 시작 - reviewId: {}", reviewId);

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 리뷰입니다. ID: " + reviewId));

        review.addLike();

        log.debug("리뷰 좋아요 증가 완료 - reviewId: {}, 현재 좋아요: {}", reviewId, review.getLikesCount());
        return convertToResponse(review);
    }

    /**
     * 리뷰를 삭제합니다.
     */
    @Transactional
    public void deleteReview(Long reviewId) {
        log.debug("리뷰 삭제 시작 - reviewId: {}", reviewId);

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 리뷰입니다. ID: " + reviewId));

        reviewRepository.delete(review);

        log.debug("리뷰 삭제 완료 - reviewId: {}", reviewId);
    }

    /**
     * Review 엔티티를 ReviewResponse로 변환합니다.
     */
    private ReviewResponse convertToResponse(Review review) {
        return ReviewResponse.from(review);
    }
}
