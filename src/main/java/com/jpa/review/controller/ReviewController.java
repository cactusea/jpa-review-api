package com.jpa.review.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jpa.review.dto.common.ApiResponse;
import com.jpa.review.dto.req.ReviewSaveReqDto;
import com.jpa.review.dto.req.ReviewUpdateReqDto;
import com.jpa.review.dto.res.ReviewResponse;
import com.jpa.review.enums.SortType;
import com.jpa.review.service.ReviewService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Tag(name = "Review API", description = "강의 리뷰 관리 API")
@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
@Slf4j
public class ReviewController {

    private final ReviewService reviewService;

    /**
     * 리뷰 목록 조회 (페이징 및 정렬 지원)
     */
    @Operation(summary = "리뷰 목록 조회", description = "페이징과 정렬을 지원하는 리뷰 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<ApiResponse<ReviewResponse>> getReviews(
            @RequestParam(value = "sort", defaultValue = "likes") SortType sortType,
            @RequestParam(value = "page", defaultValue = "0") @Min(0) int page,
            @RequestParam(value = "size", defaultValue = "10") @Min(1) @Max(50) int size) {

        log.info("리뷰 목록 조회 요청 - sortType: {}, page: {}, size: {}", sortType, page, size);

        Pageable pageable = PageRequest.of(page, size);
        ReviewResponse response = reviewService.getReviews(sortType, pageable);

        log.info("리뷰 목록 조회 완료 - 전체: {}개, 현재 페이지: {}/{}",
                response.getTotalElements(), page + 1, response.getTotalPages());

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * 단일 리뷰 조회
     */
    @Operation(summary = "단일 리뷰 조회", description = "특정 리뷰의 상세 정보를 조회합니다.")
    @GetMapping("/{reviewId}")
    public ResponseEntity<ApiResponse<ReviewResponse>> getReview(@PathVariable @Min(1) Long reviewId) {
        log.info("리뷰 단일 조회 요청 - reviewId: {}", reviewId);

        ReviewResponse response = reviewService.getReview(reviewId);

        log.info("리뷰 단일 조회 완료 - reviewId: {}, 평점: {}", reviewId, response.getRating());

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * 새 리뷰 생성
     */
    @Operation(summary = "리뷰 생성", description = "새로운 리뷰를 생성합니다.")
    @PostMapping
    public ResponseEntity<ApiResponse<ReviewResponse>> createReview(@Valid @RequestBody ReviewSaveReqDto request) {
        log.info("리뷰 생성 요청 - userId: {}, lectureId: {}, 평점: {}", 
                request.getUserId(), request.getLectureId(), request.getRating());

        ReviewResponse response = reviewService.createReview(request);

        log.info("리뷰 생성 완료 - reviewId: {}, userId: {}", response.getId(), response.getUserId());

        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(response));
    }

    /**
     * 기존 리뷰 수정
     */
    @Operation(summary = "리뷰 수정", description = "기존 리뷰를 수정합니다.")
    @PutMapping("/{reviewId}")
    public ResponseEntity<ApiResponse<ReviewResponse>> updateReview(
            @PathVariable @Min(1) Long reviewId,
            @Valid @RequestBody ReviewUpdateReqDto request) {

        log.info("리뷰 수정 요청 - reviewId: {}, 새 평점: {}", reviewId, request.getRating());

        ReviewResponse response = reviewService.updateReview(reviewId, request);

        log.info("리뷰 수정 완료 - reviewId: {}, 변경된 평점: {}", reviewId, response.getRating());

        return ResponseEntity.ok(ApiResponse.success(response));
    }

    /**
     * 리뷰 삭제
     */
    @Operation(summary = "리뷰 삭제", description = "리뷰를 삭제합니다.")
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<ApiResponse<String>> deleteReview(@PathVariable @Min(1) Long reviewId) {
        log.info("리뷰 삭제 요청 - reviewId: {}", reviewId);

        reviewService.deleteReview(reviewId);

        log.info("리뷰 삭제 완료 - reviewId: {}", reviewId);

        return ResponseEntity.ok(ApiResponse.success("리뷰가 성공적으로 삭제되었습니다."));
    }

    /**
     * 리뷰 좋아요 증가
     */
    @Operation(summary = "리뷰 좋아요 증가", description = "리뷰의 좋아요 수를 1 증가시킵니다.")
    @PostMapping("/{reviewId}/likes")
    public ResponseEntity<ApiResponse<ReviewResponse>> increaseLikes(@PathVariable @Min(1) Long reviewId) {
        log.info("리뷰 좋아요 증가 요청 - reviewId: {}", reviewId);

        ReviewResponse response = reviewService.increaseLikes(reviewId);

        log.info("리뷰 좋아요 증가 완료 - reviewId: {}, 현재 좋아요: {}",
                reviewId, response.getLikesCount());

        return ResponseEntity.ok(ApiResponse.success(response));
    }
}