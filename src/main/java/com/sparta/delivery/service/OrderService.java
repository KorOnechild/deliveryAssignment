package com.sparta.delivery.service;

import com.sparta.delivery.CreateResponseDto;
import com.sparta.delivery.dto.OrderDetailRequestDto;
import com.sparta.delivery.dto.OrderDetailResponseDto;
import com.sparta.delivery.dto.OrderRequestDto;
import com.sparta.delivery.dto.OrderResponseDto;
import com.sparta.delivery.model.Food;
import com.sparta.delivery.model.OrderDetail;
import com.sparta.delivery.model.Orders;
import com.sparta.delivery.model.Restaurant;
import com.sparta.delivery.repository.FoodRepository;
import com.sparta.delivery.repository.OrderDetailRepository;
import com.sparta.delivery.repository.OrderRepository;
import com.sparta.delivery.validator.FoodValidator;
import com.sparta.delivery.validator.OrderValidator;
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
    private final CreateResponseDto createOrderResponseDto;
    private final OrderValidator orderValidator;

    //주문 등록 로직
    public OrderResponseDto registerOrder(OrderRequestDto orderRequestDto){
        Restaurant restaurant = foodValidator.checkRestaurant(orderRequestDto.getRestaurantId());
        orderValidator.checkQuantity(orderRequestDto);
        orderValidator.checkMinorderprice(orderRequestDto, restaurant);

        List<OrderDetail> orderDetails = registerOrderDetails(orderRequestDto);

        Orders order = new Orders();
        order.setRestaurant(restaurant);
        orderRepository.save(order);

        return createOrderResponseDto.createOrderResponse(restaurant, createOrderResponseDto.createOrderDetailResponse(orderDetails) , orderDetails);
    }

    //주문 조회 로직
    public List<OrderResponseDto> getOrders() {
        return getOrderResponse();
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


    /*----------<주문 조회 ResponseDto 생성>----------*/
    private List<OrderResponseDto> getOrderResponse() {
        List<Orders> order = orderRepository.findAll();
        List<OrderDetail> orderDetails = orderDetailRepository.findAll();
        List<OrderDetailResponseDto> orderDetailResponseDtos = new ArrayList<>();
        List<OrderResponseDto> orders = new ArrayList<>();

        for(OrderDetail orderDetail : orderDetails){
            OrderDetailResponseDto orderDetailResponseDto = new OrderDetailResponseDto();

            orderDetailResponseDto.setName(orderDetail.getFood().getName());

            orderDetailResponseDto.setPrice(
                    createOrderResponseDto.calOrderDetailPrice(orderDetail.getFood().getPrice(), orderDetail.getQuantity()));

            orderDetailResponseDto.setQuantity(orderDetail.getQuantity());

            orderDetailResponseDtos.add(orderDetailResponseDto);
        }

        for (Orders value : order) {
            Restaurant restaurant = value.getRestaurant();
            orders.add(createOrderResponseDto.createOrderResponse(restaurant, orderDetailResponseDtos, orderDetails));
        }

        return orders;
    }
}
