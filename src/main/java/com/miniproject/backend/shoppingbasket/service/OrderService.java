package com.miniproject.backend.shoppingbasket.service;

import com.miniproject.backend.shoppingbasket.domain.Basket;
import com.miniproject.backend.shoppingbasket.dto.BasketResponseDTO;

public interface OrderService {

    BasketResponseDTO buyCart(String email, String productId);

    Basket findBasketByUserAndLoanProduct(String email, String productId);

}
