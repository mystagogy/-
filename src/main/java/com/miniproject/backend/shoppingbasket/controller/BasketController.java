package com.miniproject.backend.shoppingbasket.controller;

import com.miniproject.backend.global.dto.ResponseDTO;
import com.miniproject.backend.global.jwt.CustomUserDetails;
import com.miniproject.backend.shoppingbasket.dto.BasketDTO;
import com.miniproject.backend.shoppingbasket.service.BasketService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class BasketController {

    private final BasketService basketService;



    @Operation(summary = "장바구니 리스트 출력")
    @GetMapping("/cart")
    public ResponseDTO<?> showBasketList(@AuthenticationPrincipal CustomUserDetails userDetails){
        List<BasketDTO.Response> basketResponseDTO = basketService.selectBasketList(userDetails.getEmail());
        return new ResponseDTO<>().ok(basketResponseDTO,"정상출력 데이터");
    }


    @Operation(summary = "장바구니 등록")
    @PostMapping("/cart")
    public ResponseDTO<?> addBasket(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody BasketDTO.Request basketRequestDto){
        basketRequestDto.setUserEmail(userDetails.getEmail());
        BasketDTO.Response basketResponseDTO = basketService.insertBasket(basketRequestDto);
        return new ResponseDTO<>().ok(basketResponseDTO,"성공적으로 추가되었습니다!");

    }

    @Operation(summary = "장바구니 삭제")
    @DeleteMapping("/cart")
    public ResponseDTO<?> deleteBasket(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestParam Long basketId){
        String msg = basketService.deleteBasket(userDetails.getEmail(), basketId);
        return new ResponseDTO<>().ok(msg,"성공적으로 삭제되었습니다.");
    }


}
