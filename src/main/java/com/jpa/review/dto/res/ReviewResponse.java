package com.jpa.review.dto.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jpa.review.entity.Review;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewResponse {

    private Long id;
    private Long userId;
    private String userNickname;
    private String userThumbnailUrl;
    private Long lectureId;
    private String lectureTitle;
    private Integer rating;
    private Integer likesCount;
    private String content;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifiedDate;

    // 페이징 정보
    private List<ReviewResponse> reviews;
    private long totalElements;
    private int totalPages;
    private int currentPage;
    private int pageSize;

    public static ReviewResponse from(Review review) {
        return ReviewResponse.builder()
                .id(review.getId())
                .userId(review.getUser().getId())
                .userNickname(review.getUser().getNickname())
                .userThumbnailUrl(review.getUser().getThumbnailUrl())
                .lectureId(review.getLecture().getId())
                .lectureTitle(review.getLecture().getTitle())
                .rating(review.getRating())
                .likesCount(review.getLikesCount())
                .content(review.getContent())
                .createdDate(review.getCreatedDate())
                .modifiedDate(review.getModifiedDate())
                .build();
    }

    public static ReviewResponse from(org.springframework.data.domain.Page<ReviewResponse> page) {
        return ReviewResponse.builder()
                .reviews(page.getContent())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .currentPage(page.getNumber())
                .pageSize(page.getSize())
                .build();
    }
}
