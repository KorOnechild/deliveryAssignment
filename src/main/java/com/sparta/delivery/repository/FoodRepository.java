package com.sparta.delivery.repository;

import com.sparta.delivery.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FoodRepository extends JpaRepository<Food, Long> {
    Optional<Food> findAllByRestaurantIdAndName(Long restaurantId, String foodName);
}
