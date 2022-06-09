package com.sparta.delivery.validator;

import org.springframework.stereotype.Component;

@Component
public class RestaurantValidator {

    /*--------<최소 주문가격 확인>---------*/
    //허용값: 1,000원 ~ 100,000원
    //100원 단위로만 입력 가능
    //허용값이 아니거나 100원 단위 입력이 아닌 경우 에러 발생
    public void checkMinOrderPrice(Long minOrderPrice) {
        if(!(minOrderPrice >= 1000 && minOrderPrice <= 100000)){
            throw new IllegalArgumentException("1,000원 이상 100,000원 이하 가격을 입력해주세요");
        } else if (minOrderPrice % 100 != 0) {
            throw  new IllegalArgumentException("100원 단위로 입력해주세요");
        }
    }

    /*--------<기본 배달비 확인>---------*/
    //허용값 : 0원 ~ 10,000원
    //500원 단위로만 입력 가능
    public void checkdeliveryFee(Long deliveryFee) {
        if(!(deliveryFee >= 0 && deliveryFee <= 10000)){
            throw new IllegalArgumentException("0원 이상 10,000원 이하 가격을 입력해주세요");
        }else if(deliveryFee % 500 != 0){
            throw new IllegalArgumentException("500원 단위로 입력해주세요");
        }
    }
}
