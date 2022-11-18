package com.jpa.reviewsystem.dto.req;

import com.jpa.reviewsystem.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor @RequiredArgsConstructor
public class ReviewSaveReqDto {

    private Long userId;
    private Long lectureId;
    private int grade;
    private String context;

    public Review toEntity(){
        return Review.builder()
                .grade(grade)
                .context(context)
                .build();
    }
}
