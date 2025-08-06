package com.jpa.review.dto.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T>{
    private final T data;
    private final int statusCode;
    private final String message;
    private final LocalDateTime timestamp;

    // 성공 응답 (데이터 포함)
    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .data(data)
                .statusCode(HttpStatus.OK.value())
                .message("요청이 성공적으로 처리되었습니다.")
                .timestamp(LocalDateTime.now())
                .build();
    }

    // 성공 응답 (생성)
    public static <T> ApiResponse<T> created(T data) {
        return ApiResponse.<T>builder()
                .data(data)
                .statusCode(HttpStatus.CREATED.value())
                .message("리소스가 성공적으로 생성되었습니다.")
                .timestamp(LocalDateTime.now())
                .build();
    }

    // 성공 응답 (메시지만)
    public static <T> ApiResponse<T> success(String message) {
        return ApiResponse.<T>builder()
                .statusCode(HttpStatus.OK.value())
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();
    }

    // 에러 응답
    public static <T> ApiResponse<T> error(HttpStatus status, String message) {
        return ApiResponse.<T>builder()
                .statusCode(status.value())
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();
    }

    // 커스텀 응답
    public static <T> ApiResponse<T> of(HttpStatus status, String message, T data) {
        return ApiResponse.<T>builder()
                .data(data)
                .statusCode(status.value())
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();
    }

//    public static<T> ApiResponse<T> response(final T t, final int statusCode) {
//        return response(statusCode, null, t);
//    }
//
//    public static<T> ApiResponse<T> response(final int statusCode, final String msg) {
//        return response(statusCode, msg, null);
//    }
//
//    public static<T> ApiResponse<T> response(final int statusCode, final String msg, final T t) {
//        return ApiResponse.<T>builder()
//                .data(t)
//                .statusCode(statusCode)
//                .msg(msg)
//                .build();
//    }
//    public static<T> ResponseEntity<ApiResponse<T>> rResponse(final HttpStatus status, final String msg){
//        ApiResponse<T> res = response(status.value(), msg, null);
//        return ResponseEntity.status(status).body(res);
//    }
//    public static<T> ResponseEntity<ApiResponse<T>> rResponse(final HttpStatus status, final String msg, final T t){
//        ApiResponse<T> res = response(status.value(), msg, t);
//        return ResponseEntity.status(status).body(res);
//    }

}
