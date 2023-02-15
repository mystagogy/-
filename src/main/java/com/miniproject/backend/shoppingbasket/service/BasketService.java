package com.miniproject.backend.shoppingbasket.service;

import com.miniproject.backend.shoppingbasket.dto.BasketResponseDTO;

import java.util.List;

public interface BasketService {

    public List<BasketResponseDTO> selectBasketList(String email);
}
