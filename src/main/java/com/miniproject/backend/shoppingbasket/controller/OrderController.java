package com.miniproject.backend.shoppingbasket.controller;

import com.miniproject.backend.global.dto.ResponseDTO;
import com.miniproject.backend.shoppingbasket.dto.BasketResponseDTO;
import com.miniproject.backend.shoppingbasket.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@RequiredArgsConstructor
@RestController
public class OrderController {

    private final OrderService orderService;

    @Operation(description = "구매 등록하는 api")
    @PostMapping("/order")
    public ResponseDTO<?> buyCart(@RequestParam String email, @RequestParam String productId){
        BasketResponseDTO basketResponseDTO = orderService.buyCart(email, productId);
        return new ResponseDTO<>().ok(basketResponseDTO,"성공적으로 구매 완료되었습니다!");
    }
}
