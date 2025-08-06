package com.jpa.review.dto.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jpa.review.entity.Review;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewResDto {

    private Long id;
    private Long userId;
    private String userNickname;
    private Long lectureId;
    private String lectureTitle;
    private Integer rating;
    private Integer likesCount;
    private String content;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifiedDate;

    public static ReviewResDto from(Review review) {
        return ReviewResDto.builder()
                .id(review.getId())
                .userId(review.getUser().getId())
                .userNickname(review.getUser().getNickname())
                .lectureId(review.getLecture().getId())
                .lectureTitle(review.getLecture().getTitle())
                .rating(review.getRating())
                .likesCount(review.getLikesCount())
                .content(review.getContent())
                .createdDate(review.getCreatedDate())
                .modifiedDate(review.getModifiedDate())
                .build();
    }
}
