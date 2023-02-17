package com.miniproject.backend.shoppingbasket.controller;

import com.miniproject.backend.global.dto.ResponseDTO;
import com.miniproject.backend.shoppingbasket.dto.BasketResponseDTO;
import com.miniproject.backend.shoppingbasket.service.BasketService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class BasketController {

    private final BasketService basketService;


    @Operation(description = "장바구니 리스트 출력하는 api")
    @GetMapping("/cart")
    public ResponseDTO<?> showBasketList(@RequestParam String email){
        List<BasketResponseDTO> basketResponseDTO = basketService.selectBasketList(email);
        return new ResponseDTO<>().ok(basketResponseDTO,"정상출력 데이터");
    }


    @Operation(description = "장바구니 등록하는 api")
    @PostMapping("/cart")
    public ResponseDTO<?> addBasket(@RequestParam String email, @RequestParam String productId){
        BasketResponseDTO basketResponseDTO = basketService.insertBasket(email, productId);
        return new ResponseDTO<>().ok(basketResponseDTO,"성공적으로 추가되었습니다!");

    }

    @Operation(description = "장바구니 삭제하는 api")
    @DeleteMapping("/cart")
    public ResponseDTO<?> deleteBasket(@RequestParam String email, @RequestParam Long basketId){
        String msg = basketService.deleteBasket(email, basketId);
        return new ResponseDTO<>().ok(msg,"성공적으로 삭제되었습니다.");
    }


}
