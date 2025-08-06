package com.jpa.review.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "nickname", nullable = false, unique = true)
    @Size(max = 10)
    private String nickname;

    @Column(name = "thumbnail_url")
    @Size(max = 50)
    private String thumbnailUrl;

    @Builder
    public User(Long id, String nickname, String thumbnailUrl) {
        this.id = id;
        this.nickname = nickname;
        this.thumbnailUrl = thumbnailUrl;
    }
}
