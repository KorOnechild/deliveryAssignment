package com.sparta.delivery.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
@Getter
public class OrderDetailResponseDto {
    private String name;
    private Long quantity;
    private Long price;

    public OrderDetailResponseDto(String foodName, Long quantity, Long price){
        this.name = foodName;
        this.quantity = quantity;
        this.price = price;
    }
}
