package com.sparta.delivery.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Setter
@Getter
public class OrderResponseDto {
    private String restaurantName;
    private List<OrderDetailResponseDto> foods;

    private Long deliveryFee;

    private Long totalPrice;

    public OrderResponseDto(String restaurantName, List<OrderDetailResponseDto> orderResponseDtos){
        this.restaurantName = restaurantName;
        this.foods = orderResponseDtos;
    }
}
