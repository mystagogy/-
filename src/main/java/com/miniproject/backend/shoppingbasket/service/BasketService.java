package com.miniproject.backend.shoppingbasket.service;

import com.miniproject.backend.shoppingbasket.dto.BasketResponseDTO;

import java.util.List;

public interface BasketService {

    List<BasketResponseDTO> selectBasketList(String email);

    BasketResponseDTO insertBasket(String email, String productId);

    String deleteBasket(String email,Long basketId);
}
