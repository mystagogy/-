package com.miniproject.backend.shoppingbasket.service;

import com.miniproject.backend.shoppingbasket.domain.Basket;
import com.miniproject.backend.shoppingbasket.dto.BasketResponseDTO;

import java.util.List;

public interface OrderService {

    BasketResponseDTO buyCart(String email, String productId);

    Basket findBasketByUserAndLoanProduct(String email, String productId);

    List<BasketResponseDTO> selectBuyList(String email);

    String deleteBuy(String email,Long basketId);

}
