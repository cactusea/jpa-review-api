package com.jpa.reviewsystem.dto.res;

import com.jpa.reviewsystem.entity.Review;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class ReviewResDto {

    private Long id;
    private int grade; //
    private int like;
    private String context;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime modifiedDate;

    public ReviewResDto toDto(Review review){
        return ReviewResDto.builder()
                .id(review.getId())
                .grade(review.getGrade())
                .like(review.getLike())
                .createdDate(review.getCreatedDate())
                .modifiedDate(review.getModifiedDate())
                .context(review.getContext())
                .build();
    }
}
