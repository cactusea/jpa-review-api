package com.jpa.review.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum SortType {
    LIKES("likes", "likesCount", Sort.Direction.DESC),
    LATEST("latest", "createdDate", Sort.Direction.DESC),
    RATING_HIGH("rating_high", "rating", Sort.Direction.DESC),
    RATING_LOW("rating_low", "rating", Sort.Direction.ASC);

    private final String value;
    private final String property;
    private final Sort.Direction direction;

    /**
     * 문자열 값으로부터 SortType을 찾아 반환
     */
    public static SortType fromString(String value) {
        return Arrays.stream(values())
                .filter(sortType -> sortType.getValue().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(
                        "지원하지 않는 정렬 타입입니다. 사용 가능한 값: " +
                                Arrays.toString(Arrays.stream(values()).map(SortType::getValue).toArray())
                ));
    }

    /**
     * Spring Data의 Sort 객체로 변환
     */
    public Sort toSort() {
        return Sort.by(direction, property);
    }
}
