package com.localservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.localservice.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
