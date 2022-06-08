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
//    @JsonIgnore
    private Long id;

    @Column(nullable = false)
    private Long quantity;

    @OneToOne
    @JoinColumn(name = "foodId")
    private Food food;

    @Column(nullable = false)
    private Long price;

    public OrderDetail(Long quantity, Food food){
        this.quantity = quantity;
        this.food = food;
    }
}
