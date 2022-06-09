package com.sparta.delivery.service;

import com.sparta.delivery.dto.OrderDetailRequestDto;
import com.sparta.delivery.dto.OrderDetailResponseDto;
import com.sparta.delivery.dto.OrderRequestDto;
import com.sparta.delivery.dto.OrderResponseDto;
import com.sparta.delivery.model.Food;
import com.sparta.delivery.model.Orders;
import com.sparta.delivery.model.OrderDetail;
import com.sparta.delivery.model.Restaurant;
import com.sparta.delivery.repository.FoodRepository;
import com.sparta.delivery.repository.OrderDetailRepository;
import com.sparta.delivery.repository.OrderRepository;
import com.sparta.delivery.validator.FoodValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final FoodRepository foodRepository;
    private final FoodValidator foodValidator;

    //주문 등록 로직
    public OrderResponseDto registerOrder(OrderRequestDto orderRequestDto){
        Restaurant restaurant = foodValidator.checkRestaurant(orderRequestDto.getRestaurantId());

        List<OrderDetail> orderDetails = registerOrderDetails(orderRequestDto);

        Orders order = new Orders();
        orderRepository.save(order);

        return createOrderResponse(restaurant, createOrderDetailResponse(orderDetails), orderDetails);
    }

    /*----------<OrderDetail 등록>----------*/
    public List<OrderDetail> registerOrderDetails(OrderRequestDto orderRequestDto){
        List<OrderDetailRequestDto> orderDetails = orderRequestDto.getFoods();

        for(OrderDetailRequestDto orderDetailRequestDto : orderDetails){
            Food food = foodRepository.findById(orderDetailRequestDto.getId()).orElseThrow(
                    ()-> new IllegalArgumentException("선택하신 메뉴가 존재하지 않습니다.")
            );

            OrderDetail orderDetail = new OrderDetail(food, orderDetailRequestDto.getQuantity());
            orderDetailRepository.save(orderDetail);
        }
        return orderDetailRepository.findAll();
    }

    /*----------<totalPrice 계산>----------*/
    public Long calTotalPrice(List<OrderDetail> orderDetails, Long deliveryFee){
        Long totalPrice = deliveryFee;
        for(OrderDetail orderDetail : orderDetails){
            Long quantity = orderDetail.getQuantity();
            Long price = calOrderDetailPrice(orderDetail.getFood().getPrice(), quantity);
            totalPrice += price;
        }
        return totalPrice;
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
