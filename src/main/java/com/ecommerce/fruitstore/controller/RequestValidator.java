package com.ecommerce.fruitstore.controller;

import com.ecommerce.fruitstore.domain.Errors;
import com.ecommerce.fruitstore.domain.Error;
import com.ecommerce.fruitstore.domain.OrderRequest;
import com.ecommerce.fruitstore.domain.OrderSummary;

/** do some simple validation on the order request */
public class RequestValidator {

    public Errors validateOrderRequest(OrderRequest orderRequest) {
        Errors errors = new Errors();

        if (orderRequest == null) {
            errors.addError(new Error("Order Request is null"));
        } else if (orderRequest.getApples() < 0 || orderRequest.getOranges() < 0) {
            errors.addError(new Error("Order should have positive quantity for either Apples or Oranges or both"));
        } else if (orderRequest.getApples() == 0 || orderRequest.getOranges() == 0) {
            errors.addError(new Error("Order should have positive quantity for either Apples or Oranges "));
        }

        if(orderRequest.getApplePromotionCodes() != null && orderRequest.getApplePromotionCodes().size() > 0){
            orderRequest.getApplePromotionCodes().forEach(promoCode -> {
                if(promoCode > 100 ){ // Assuming the promotion code is between 1 and 100
                    errors.addError(new Error("Invalid Apple Promotion Code " + promoCode));
                }
            });

            orderRequest.getOrangePromotionCodes().forEach(promoCode -> {
                if( promoCode > 100  ){ // Assuming the promotion code is between 1 and 100
                    errors.addError(new Error("Invalid Orange Promotion Code " + promoCode));
                }
            });
        }
        return errors;
    }
}