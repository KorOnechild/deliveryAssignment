package com.sparta.delivery.service;

import com.sparta.delivery.dto.FoodRequestDto;
import com.sparta.delivery.model.Food;
import com.sparta.delivery.model.Restaurant;
import com.sparta.delivery.repository.FoodRepository;
import com.sparta.delivery.validator.FoodValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FoodService {

    private final FoodValidator foodValidator;
    private final FoodRepository foodRepository;

    //음식 메뉴 등록 로직
    public void registerFoodMenu(Long restaurantId, List<FoodRequestDto> foodRequestDto) {

        //입력 음식값 중복 체크
        foodValidator.checkFoodname(foodRequestDto);
        //음식 가격값 체크
        foodValidator.checkPrice(foodRequestDto);
        //유효성 검사
        Restaurant restaurant = foodValidator.checkRestaurant(restaurantId);
        //메뉴에서 음식명 중복 체크
        foodValidator.checkFoodnameInMenu(restaurantId, foodRequestDto);

        //음식 메뉴 생성
        for(int i = 0; i< foodRequestDto.size(); i++){
            Food food = new Food(restaurantId, foodRequestDto.get(i));

            restaurant.getFoods().add(food);
            foodRepository.save(food);
        }
    }

    //메뉴 조회 로직
    public List<Food> getMenu(Long restaurantId) {
        Restaurant restaurant = foodValidator.checkRestaurant(restaurantId);
        return (List<Food>) restaurant.getFoods();
    }
}
