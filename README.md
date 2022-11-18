# JPA를 적용한 강의 리뷰 서비스 API

[RESTDocs API 명세(adoc)](src/docs/asciidoc/index.adoc)  

1. 강의 리뷰를 등록, 조회, 수정, 삭제하는 기능을 제공합니다.
2. 리뷰에 좋아요(+1)를 할 수 있습니다.
3. 강의 리뷰 리스트를 Page로 리턴합니다.
   1. 기본 정렬: 좋아요 많은 순(like, desc)
   2. 1 페이지당 데이터 사이즈: 10

### 기술 스텍
* Java / JDK 11
* SpringBoot 2.7.5
* Spring Data JPA 2.7.5
* RDB - MySQL
