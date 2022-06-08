package com.sparta.delivery.service;

import com.sparta.delivery.dto.RestaurantDto;
import com.sparta.delivery.model.Restaurant;
import com.sparta.delivery.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository){
        this. restaurantRepository = restaurantRepository;
    }

    //음식점 등록 로직
    public Restaurant resgisterRestaurant(RestaurantDto restaurantDto) {
        //최소 주문가격 입력값 검사
        checkMinOrderPrice(restaurantDto.getMinOrderPrice());
        //배달비 입력값 검사
        checkdeliveryFee(restaurantDto.getDeliveryFee());

        Restaurant restaurant = new Restaurant(restaurantDto);
        restaurantRepository.save(restaurant);
        return restaurant;
    }

    //등록된 모든 음식점 정보 조회
    public List<Restaurant> getRestaurantList() {
        return restaurantRepository.findAll();
    }



    /*--------<최소 주문가격 확인>---------*/
    //허용값: 1,000원 ~ 100,000원
    //100원 단위로만 입력 가능
    //허용값이 아니거나 100원 단위 입력이 아닌 경우 에러 발생
    private void checkMinOrderPrice(Long minOrderPrice) {
        if(!(minOrderPrice >= 1000 && minOrderPrice <= 100000)){
            throw new IllegalArgumentException("1,000원 이상 100,000원 이하 가격을 입력해주세요");
        } else if (minOrderPrice % 100 != 0) {
            throw  new IllegalArgumentException("100원 단위로 입력해주세요");
        }
    }

    /*--------<기본 배달비 확인>---------*/
    //허용값 : 0원 ~ 10,000원
    //500원 단위로만 입력 가능
    private void checkdeliveryFee(Long deliveryFee) {
        if(!(deliveryFee >= 0 && deliveryFee <= 10000)){
            throw new IllegalArgumentException("0원 이상 10,000원 이하 가격을 입력해주세요");
        }else if(deliveryFee % 500 != 0){
            throw new IllegalArgumentException("500원 단위로 입력해주세요");
        }
    }
}
