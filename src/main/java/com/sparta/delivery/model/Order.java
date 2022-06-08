package com.sparta.delivery.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;

    @Column(nullable = false)
    private String restaurantName;

    @Column(nullable = false)
    private Long deliveryFee;

    @Column(nullable = false)
    private Long totalPrice;

    @OneToMany
    @JoinColumn
    private List<OrderDetail> foods;

    public Order(String restaurantName,
                 Long deliveryFee,
                 Long totalPrice,
                 List<OrderDetail> orderRequestDto){

        this.restaurantName = restaurantName;
        this.deliveryFee = deliveryFee;
        this.totalPrice = totalPrice;
        this.foods = orderRequestDto;
    }
}
