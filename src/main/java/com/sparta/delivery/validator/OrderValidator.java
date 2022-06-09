package com.sparta.delivery.validator;

import com.sparta.delivery.CreateResponseDto;
import com.sparta.delivery.dto.OrderRequestDto;
import com.sparta.delivery.model.Food;
import com.sparta.delivery.model.Restaurant;
import com.sparta.delivery.repository.FoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class OrderValidator {


    private final FoodRepository foodRepository;
    private final CreateResponseDto createResponseDto;

    /*-----------<주문 수량 체크>----------*/
    //허용값 : 1개 ~ 100개
    public void checkQuantity(OrderRequestDto orderRequestDto){
        for(int i=0; i<orderRequestDto.getFoods().size(); i++){
            Long quantity = orderRequestDto.getFoods().get(i).getQuantity();
            if(!(quantity >= 1 && quantity <= 100)){
                throw new IllegalArgumentException("주문 수량을 1개 이상 100이상 입력해주세요");
            }
        }
    }
    /*-----------<최소 주문 금액 체크>----------*/
    public void checkMinorderprice(OrderRequestDto orderRequestDto, Restaurant restaurant) {
        Long totalPrice = 0L;
        for(int i=0; i<orderRequestDto.getFoods().size(); i++){
            Food food = foodRepository.findById(orderRequestDto.getFoods().get(i).getId()).orElseThrow(
                    () -> new IllegalArgumentException("해당 메뉴가 없습니다.")
            );
            Long price = food.getPrice();
            Long quantity = orderRequestDto.getFoods().get(i).getQuantity();
            totalPrice += createResponseDto.calOrderDetailPrice(price, quantity);
        }
        if(totalPrice < restaurant.getMinOrderPrice()){
            throw new IllegalArgumentException("최소 주문 금액 이상 주문해주세요");
        }
    }
}
