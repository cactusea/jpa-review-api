package com.jpa.review.dto.req;

import com.jpa.review.entity.Review;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewSaveReqDto {

    @NotNull(message = "사용자 ID는 필수입니다.")
    private Long userId;

    @NotNull(message = "강의 ID는 필수입니다.")
    private Long lectureId;

    @NotNull(message = "평점은 필수입니다.")
    @Min(value = 1, message = "평점은 1점 이상이어야 합니다.")
    @Max(value = 5, message = "평점은 5점 이하여야 합니다.")
    private Integer rating;

    @NotNull(message = "리뷰 내용은 필수입니다.")
    @Size(max = 1000, message = "리뷰 내용은 1000자 이하여야 합니다.")
    private String content;

    public Review toEntity() {
        return Review.builder()
                .rating(rating)
                .content(content)
                .likesCount(0)
                .build();
    }
}
