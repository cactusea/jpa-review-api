package com.jpa.review.common;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Builder
public class ReviewResponse<T>{
    private T data;
    private int statusCode;
    private String msg;

    public static<T> ReviewResponse<T> response(final T t, final int statusCode) {
        return response(statusCode, null, t);
    }

    public static<T> ReviewResponse<T> response(final int statusCode, final String msg) {
        return response(statusCode, msg, null);
    }

    public static<T> ReviewResponse<T> response(final int statusCode, final String msg, final T t) {
        return ReviewResponse.<T>builder()
                .data(t)
                .statusCode(statusCode)
                .msg(msg)
                .build();
    }
    public static<T> ResponseEntity<ReviewResponse<T>> rResponse(final HttpStatus status, final String msg){
        ReviewResponse<T> res = response(status.value(), msg, null);
        return ResponseEntity.status(status).body(res);
    }
    public static<T> ResponseEntity<ReviewResponse<T>> rResponse(final HttpStatus status, final String msg, final T t){
        ReviewResponse<T> res = response(status.value(), msg, t);
        return ResponseEntity.status(status).body(res);
    }

}
