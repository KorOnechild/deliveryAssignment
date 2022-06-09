package com.sparta.delivery;

import com.sparta.delivery.dto.OrderDetailResponseDto;
import com.sparta.delivery.dto.OrderResponseDto;
import com.sparta.delivery.model.OrderDetail;
import com.sparta.delivery.model.Restaurant;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CreateResponseDto {

    /*----------<totalPrice 계산>----------*/
    public Long calTotalPrice(List<OrderDetail> orderDetails, Long deliveryFee){
         Long totalPrice = 0L;
        for(OrderDetail orderDetail : orderDetails){
            Long quantity = orderDetail.getQuantity();
            Long price = calOrderDetailPrice(orderDetail.getFood().getPrice(), quantity);
            totalPrice += price;
        }
        return totalPrice + deliveryFee;
    }

    /*----------<orderDetail price 계산>----------*/
    public Long calOrderDetailPrice(Long price, Long quantity){
        return price * quantity;
    }


    /*----------<OrderDetailResponseDto 생성>----------*/
    public List<OrderDetailResponseDto> createOrderDetailResponse(List<OrderDetail> orderDetails){
        List<OrderDetailResponseDto> orderDetailResponseDtoList = new ArrayList<>();

        for(OrderDetail orderDetail : orderDetails){
            String foodName = orderDetail.getFood().getName();
            Long quantity = orderDetail.getQuantity();

            Long price = calOrderDetailPrice(orderDetail.getFood().getPrice(), quantity);
            OrderDetailResponseDto orderDetailResponseDto = new OrderDetailResponseDto(foodName, quantity, price);
            orderDetailResponseDtoList.add(orderDetailResponseDto);
        }

        return orderDetailResponseDtoList;
    }

    /*----------<OrderResponseDto 생성>----------*/
    public OrderResponseDto createOrderResponse(Restaurant restaurant, List<OrderDetailResponseDto> orderDetailResponseDto, List<OrderDetail> orderDetails){
        OrderResponseDto orderResponseDto = new OrderResponseDto();
        orderResponseDto.setRestaurantName(restaurant.getName());
        orderResponseDto.setFoods(orderDetailResponseDto);
        orderResponseDto.setDeliveryFee(restaurant.getDeliveryFee());
        orderResponseDto.setTotalPrice(calTotalPrice(orderDetails, restaurant.getDeliveryFee()));
        return orderResponseDto;
    }
}
