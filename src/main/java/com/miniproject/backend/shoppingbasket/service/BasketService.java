package com.miniproject.backend.shoppingbasket.service;

import com.miniproject.backend.shoppingbasket.domain.Basket;
import com.miniproject.backend.shoppingbasket.dto.BasketDTO;

import java.util.List;

public interface BasketService {

    List<BasketDTO.Response> selectBasketList(String email);

    BasketDTO.Response insertBasket(String email,BasketDTO.Request basketRequestDto);

    String deleteBasket(String email,Long basketId);

    Basket findBasketByIdAndUser(String email, Long basketId);
}
