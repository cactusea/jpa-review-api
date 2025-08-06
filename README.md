# JPA Review API

강의 리뷰 서비스를 위한 REST API입니다.

## 기술 스택

- **Java**: 17
- **Spring Boot**: 3.2.3
- **Spring Data JPA**: 3.2.3
- **Gradle**: 8.5
- **MySQL**: 8.0+
- **Docker**: 20.0+
- **Swagger/OpenAPI**: 3.0

## 주요 기능

### 리뷰 관리

- ✅ 리뷰 생성 (POST `/api/v1/reviews`)
- ✅ 리뷰 조회 (GET `/api/v1/reviews/{reviewId}`)
- ✅ 리뷰 목록 조회 (GET `/api/v1/reviews`)
- ✅ 리뷰 수정 (PUT `/api/v1/reviews/{reviewId}`)
- ✅ 리뷰 삭제 (DELETE `/api/v1/reviews/{reviewId}`)
- ✅ 리뷰 좋아요 (POST `/api/v1/reviews/{reviewId}/likes`)

### 정렬 옵션

- 좋아요 순 (기본값)
- 최신 순
- 높은 평점 순
- 낮은 평점 순

### 페이징

- 기본 페이지 크기: 10개
- 최대 페이지 크기: 50개

## 실행 방법

### 1. Docker를 사용한 실행 (권장)

#### 사전 요구사항

- Docker Desktop 설치 및 실행
- Docker Compose 지원

#### 실행 명령

```bash
# 애플리케이션 빌드 및 실행
docker compose up --build

# 백그라운드 실행
docker compose up -d --build

# 로그 확인
docker compose logs -f

# 중지
docker compose down
```

### 2. 로컬 개발 환경

#### 사전 요구사항

- Java 17
- MySQL 8.0+
- Gradle 8.5+

#### 애플리케이션 실행

```bash
# 의존성 설치
./gradlew build -x test

# 애플리케이션 실행
./gradlew bootRun
```

#### 테스트 실행

```bash
./gradlew test
```
