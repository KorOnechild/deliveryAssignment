package com.sparta.delivery.validator;

import com.sparta.delivery.dto.FoodRequestDto;
import com.sparta.delivery.model.Restaurant;
import com.sparta.delivery.repository.FoodRepository;
import com.sparta.delivery.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FoodValidator {
    private final RestaurantRepository restaurantRepository;
    private final FoodRepository foodRepository;

    @Autowired
    public FoodValidator(RestaurantRepository restaurantRepository, FoodRepository foodRepository){
        this.restaurantRepository = restaurantRepository;
        this.foodRepository  = foodRepository;
    }

    /*--------<음식점id 유효성 검사>---------*/
    public Restaurant checkRestaurant(Long restaurantId) {
        return restaurantRepository.findById(restaurantId).orElseThrow(
                () -> new IllegalArgumentException("해당 음식점이 존재하지 않습니다.")
        );
    }

    /*--------<입력 음식값 중복 체크>---------*/
    //같은 메뉴명을 입력할 수 없음
    public void checkFoodname(List<FoodRequestDto> foodRequestDtos) {
        for(int i = 0; i < foodRequestDtos.size(); i++){
            for(int j = (i + 1); j < foodRequestDtos.size(); j++)
                if(foodRequestDtos.get(i).getName().equals(foodRequestDtos.get(j).getName())){
                    throw new IllegalArgumentException("음식이름이 중복되었습니다.");
                }
        }
    }
    /*--------<음식 가격값 체크>---------*/
    //허용값 : 100원 ~ 1,000,000원
    //100원 단위로만 입력 가능
    public void checkPrice(List<FoodRequestDto> foodRequestDtos) {
        for(FoodRequestDto foodRequestDto : foodRequestDtos){
            if(!(foodRequestDto.getPrice() >= 100 && foodRequestDto.getPrice() <= 1000000)){
                throw new IllegalArgumentException("100원 이상 1,000,000원 이하로 입력해주세요");
            }else if(foodRequestDto.getPrice() % 100 != 0){
                throw new IllegalArgumentException("100원 단위로 입력해주세요");
            }
        }
    }

    /*--------<메뉴에서 음식명 중복 체크>---------*/
    public void checkFoodnameInMenu(Long restaurantId, List<FoodRequestDto> foodRequestDto) {

        for(int i = 0; i< foodRequestDto.size(); i++){
            String foodName = foodRequestDto.get(i).getName();
            if(foodRepository.findAllByRestaurantIdAndName(restaurantId, foodName).isPresent()){
                throw new IllegalArgumentException("이미 저장된 음식명입니다.");
            }
        }
    }

}
