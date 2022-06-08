package com.sparta.delivery.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestaurantDto {
    private String name;
    private Long minOrderPrice;
    private Long deliveryFee;
}
