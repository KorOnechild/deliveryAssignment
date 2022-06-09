package com.sparta.delivery.service;

import com.sparta.delivery.dto.RestaurantDto;
import com.sparta.delivery.model.Restaurant;
import com.sparta.delivery.repository.RestaurantRepository;
import com.sparta.delivery.validator.RestaurantValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantValidator restaurantValidator;
    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository, RestaurantValidator restaurantValidator){
        this. restaurantRepository = restaurantRepository;
        this. restaurantValidator = restaurantValidator;
    }

    //음식점 등록 로직
    public Restaurant resgisterRestaurant(RestaurantDto restaurantDto) {
        //최소 주문가격 입력값 검사
        restaurantValidator.checkMinOrderPrice(restaurantDto.getMinOrderPrice());
        //배달비 입력값 검사
        restaurantValidator.checkdeliveryFee(restaurantDto.getDeliveryFee());

        Restaurant restaurant = new Restaurant(restaurantDto);
        restaurantRepository.save(restaurant);
        return restaurant;
    }

    //등록된 모든 음식점 정보 조회
    public List<Restaurant> getRestaurantList() {
        return restaurantRepository.findAll();
    }
}
