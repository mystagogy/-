package com.miniproject.backend.shoppingbasket.service;

import com.miniproject.backend.shoppingbasket.domain.Basket;
import com.miniproject.backend.shoppingbasket.dto.BasketDTO;

import java.util.List;

public interface OrderService {

    BasketDTO.buyResponse buyCart(String email, BasketDTO.Request basketRequestDto);

    Basket findBasketByUserAndLoanProduct(String email, String productId);

    List<BasketDTO.Response> selectBuyList(String email);

    String deleteBuy(String email,Long basketId);

}
