package com.sparta.delivery.controller;

import com.sparta.delivery.dto.FoodRequestDto;
import com.sparta.delivery.model.Food;
import com.sparta.delivery.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FoodController {

    private final FoodService foodService;

    @Autowired
    public FoodController(FoodService foodService){
        this.foodService = foodService;
    }

    @PostMapping("/restaurant/{restaurantId}/food/register")
    public void registerFood(@PathVariable Long restaurantId, @RequestBody List<FoodRequestDto> foodRequestDto){
        foodService.registerFoodMenu(restaurantId, foodRequestDto);
    }

    //음식점 메뉴판 조회
    @GetMapping("restaurant/{restaurantId}/foods")
    public List<Food> getMenu(@PathVariable Long restaurantId){
        return foodService.getMenu(restaurantId);
    }
}
