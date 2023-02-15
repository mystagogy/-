package com.miniproject.backend.shoppingbasket.controller;

import com.miniproject.backend.global.dto.ResponseDTO;
import com.miniproject.backend.shoppingbasket.dto.BasketResponseDTO;
import com.miniproject.backend.shoppingbasket.service.BasketService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class BasketController {

    private final BasketService basketService;


    @Operation(description = "장바구니 리스트 출력하는 api")
    @GetMapping("/cart")
    public ResponseDTO<?> showBasketList(String email){
        List<BasketResponseDTO> basketResponseDTO = basketService.selectBasketList(email);
        return new ResponseDTO<>().ok(basketResponseDTO,"정상출력 데이터");
    }
}
