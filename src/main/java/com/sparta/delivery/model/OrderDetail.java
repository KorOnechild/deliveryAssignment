package com.sparta.delivery.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "foodId", nullable = false)
    private Food food;

    @Column(nullable = false)
    private Long quantity;

    @ManyToOne
    @JoinColumn(name = "orderId")
    private Orders order;

    public OrderDetail(Food food, Long quantity){
        this.food = food;
        this.quantity = quantity;
    }
}
