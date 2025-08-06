package com.jpa.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jpa.review.entity.Lecture;

@Repository
public interface LectureRepository extends JpaRepository<Lecture, Long> {
}
