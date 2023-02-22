package com.miniproject.backend.shoppingbasket.controller;

import com.miniproject.backend.global.dto.ResponseDTO;
import com.miniproject.backend.global.jwt.CustomUserDetails;
import com.miniproject.backend.shoppingbasket.dto.BasketDTO;
import com.miniproject.backend.shoppingbasket.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "구매 등록")
    @PostMapping("/order")
    public ResponseDTO<?> buyCart(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody BasketDTO.Request basketRequestDto){
        BasketDTO.buyResponse basketResponseDTO = orderService.buyCart(userDetails.getEmail(), basketRequestDto);
        return new ResponseDTO<>().ok(basketResponseDTO,"성공적으로 구매 완료되었습니다!");
    }

    @Operation(summary = "구매 리스트 출력")
    @GetMapping("/order")
    public ResponseDTO<?> showBuyList(@AuthenticationPrincipal CustomUserDetails userDetails){
        List<BasketDTO.Response> basketResponseDTO = orderService.selectBuyList(userDetails.getEmail());
        return new ResponseDTO<>().ok(basketResponseDTO,"정상출력 데이터");
    }

    @Operation(summary = "구매 삭제")
    @DeleteMapping("/order")
    public ResponseDTO<?> deleteBuy(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestParam Long basketId){
        String msg = orderService.deleteBuy(userDetails.getEmail(), basketId);
        return new ResponseDTO<>().ok(msg,"성공적으로 삭제되었습니다.");
    }
}
