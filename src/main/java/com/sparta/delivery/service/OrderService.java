package com.sparta.delivery.service;

import com.sparta.delivery.dto.OrderRequestDto;
import com.sparta.delivery.model.Food;
import com.sparta.delivery.model.Order;
import com.sparta.delivery.model.OrderDetail;
import com.sparta.delivery.model.Restaurant;
import com.sparta.delivery.repository.FoodRepository;
import com.sparta.delivery.repository.OrderDetailRepository;
import com.sparta.delivery.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final FoodService foodService;
    private final FoodRepository foodRepository;
    private final OrderDetailRepository orderDetailRepository;

    //주문 등록 로직
    public Order registerOrder(OrderRequestDto orderRequestDto) {
        Restaurant restaurant = foodService.checkRestaurant(orderRequestDto.getRestaurantId());
        List<OrderDetail> orderDetails = saveOrderDetail(orderRequestDto);
        checkQuantity(orderDetails);


        Order order = new Order(restaurant.getName(),
                                restaurant.getDeliveryFee(),
                                getTotalPrice(orderDetails),
                                orderDetails);


        return orderRepository.save(order);
    }


    /*------------<주문 상세내용 저장>-----------*/
    private List<OrderDetail> saveOrderDetail(OrderRequestDto orderRequestDto) {
        List<OrderDetail> orderDetails = new ArrayList<>();

        //주문 저장을 위해 주문상세내용 저장(주문 테이블에 주문 상세내용 리스트가 있기 때문)
        for(int i = 0; i< orderRequestDto.getFoods().size(); i++){
            Long foodId = orderRequestDto.getFoods().get(i).getId();
            Long quantity = orderRequestDto.getFoods().get(i).getQuantity();
            Food food = foodRepository.findById(foodId).orElseThrow(
                    () -> new IllegalArgumentException("해당 음식이 존재하지 않습니다.")
            );
            OrderDetail orderDetail = new OrderDetail(quantity, food);
            orderDetail.setPrice(calculatatePrice(orderDetail, quantity));

            orderDetailRepository.save(orderDetail);
            orderDetails.add(orderDetail);
        }

        return orderDetails;
    }

    /*------------<음식 주문 수량 확인>-----------*/
    public void checkQuantity(List<OrderDetail> orderDetails){
        for (OrderDetail orderDetail : orderDetails) {
            Long quantity = orderDetail.getQuantity();
            if (!(quantity >= 1 && quantity <= 100)) {
                throw new IllegalArgumentException("주문수량을 1개에서 100개 사이로 입력해주세요");
            }
        }
    }

    /*------------<주문 음식 가격 계산>-----------*/
    public Long calculatatePrice(OrderDetail orderDetail, Long quantity){
        return orderDetail.getFood().getPrice() * quantity;
    }

    /*------------<최종 결제 금액 계산>-----------*/
    public Long getTotalPrice(List<OrderDetail> orderDetail){
        Long totalPrice = 0L;
        for(int i=0; i<orderDetail.size(); i++) {
            totalPrice += orderDetail.get(i).getPrice();
        }
        return totalPrice;
    }

}
