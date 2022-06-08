package com.sparta.delivery.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.delivery.dto.FoodRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @JsonIgnore
    @Column(nullable = false)
    private Long restaurantId;
    //연관관계 매핑시 객체 자체를 매핑시키는게 모든 엔티티연관관계에 있어서 고려(음식점이 삭제될때 음식이 사라지는것 고려)
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long price;

    public Food(Long restaurantId, FoodRequestDto foodRequestDto){
        this.restaurantId = restaurantId;
        this.name = foodRequestDto.getName();
        this.price = foodRequestDto.getPrice();
    }

}
