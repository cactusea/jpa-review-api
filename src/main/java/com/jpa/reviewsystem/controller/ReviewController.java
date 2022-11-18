package com.jpa.reviewsystem.controller;

import com.jpa.reviewsystem.common.ReviewResponse;
import com.jpa.reviewsystem.dto.req.ReviewSaveReqDto;
import com.jpa.reviewsystem.dto.res.ReviewSaveResDto;
import com.jpa.reviewsystem.dto.res.ReviewResDto;
import com.jpa.reviewsystem.dto.req.ReviewUpdateReqDto;
import com.jpa.reviewsystem.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
@Slf4j
public class ReviewController {

    private final ReviewService reviewService;

    /**
     * 강의에 대한 리뷰 리스트 조회
     * 기본 정렬: like, desc
     * 페이지 크기: 10
     * @param lectureId
     * @param pageable
     * @return
     */
    @GetMapping()
    public ResponseEntity<ReviewResponse<Page<ReviewResDto>>> getReviewPage(@RequestParam("lecture") Long lectureId,
                                                                            @PageableDefault(sort={"like"}, size = 10, direction = Sort.Direction.DESC) Pageable pageable){

        Page<ReviewResDto> reviewResPage = reviewService.getReviewPage(lectureId, pageable);

        if(reviewResPage!=null){
            return ReviewResponse.rResponse(HttpStatus.OK, "OK", reviewResPage);
        }else{
            return ReviewResponse.rResponse(HttpStatus.BAD_REQUEST, "not a valid sort condition.");
        }
    }

    /**
     * 리뷰 단건 조회
     * @param reviewId
     * @return
     */
    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewResponse<ReviewResDto>> getReview(@PathVariable Long reviewId){
        ReviewResDto resDto = reviewService.getReview(reviewId);

        if(resDto!=null){
            return ReviewResponse.rResponse(HttpStatus.OK, "OK", resDto);
        }else{
            return ReviewResponse.rResponse(HttpStatus.NOT_FOUND, "review is no searched");
        }
    }

    /**
     * 리뷰 생성
     * @param reviewReq
     */
    @PostMapping()
    public ResponseEntity<ReviewResponse<ReviewSaveResDto>> createReview(@RequestBody ReviewSaveReqDto reviewReq){
        ReviewSaveResDto resDto = new ReviewSaveResDto();
        Long result = reviewService.saveReview(reviewReq);

        if(result!=null){
            resDto.setReviewId(reviewService.saveReview(reviewReq));
            return ReviewResponse.rResponse(HttpStatus.OK, "OK", resDto);
        }else{
            return ReviewResponse.rResponse(HttpStatus.NOT_FOUND, "No value present", resDto);
        }

    }

    /**
     * 리뷰 수정
     * @param reviewReq
     * @param reviewId
     */
    @PatchMapping("/{reviewId}")
    public ResponseEntity<ReviewResponse<ReviewSaveResDto>> updateReview(@RequestBody ReviewUpdateReqDto reviewReq, @PathVariable Long reviewId){
        ReviewSaveResDto resDto = new ReviewSaveResDto();
        resDto.setReviewId(reviewService.updateReview(reviewReq, reviewId));

        return ReviewResponse.rResponse(HttpStatus.OK, "OK", resDto);
    }

    /**
     * 리뷰 좋아요 +1
     * @param reviewId
     */
    @PatchMapping("/like/{reviewId}")
    public ResponseEntity<ReviewResponse<Long>> addLikeReview(@PathVariable Long reviewId){
        reviewService.addLikeReview(reviewId);
        return ReviewResponse.rResponse(HttpStatus.OK, "OK", reviewId);
    }

    /**
     * 리뷰 삭제
     * @param reviewId
     */
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<ReviewResponse<Boolean>> deleteReview(@PathVariable Long reviewId){
        Boolean result = reviewService.deleteReview(reviewId);
        return ReviewResponse.rResponse(HttpStatus.OK, "OK", result);
    }

}
