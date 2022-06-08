package com.sparta.delivery.dto;

import lombok.Getter;

@Getter
public class RestaurantDto {
    private String name;
    private Long minOrderPrice;
    private Long deliveryFee;
}
