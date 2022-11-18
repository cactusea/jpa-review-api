package com.jpa.reviewsystem.dto.req;

import com.jpa.reviewsystem.entity.Review;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ReviewUpdateReqDto {

    private Long id;

    private int grade;
    private int like;
    private String context;


    public Review toEntity(){
        return Review.builder()
                .id(id)
                .grade(grade)
                .like(like)
                .context(context)
                .build();
    }

}
