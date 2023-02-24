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


    /**
     * 해당 사용자의 장바구니 리스트 출력하는 API
     * @param userDetails : 유저정보를 가진 token 객체
     * @return 사용자가 등록한 상품정보 DTO 리스트 + "정상출력 데이터"
     */
    @Operation(summary = "장바구니 리스트 출력")
    @GetMapping("/cart")
    public ResponseDTO<?> showBasketList(@AuthenticationPrincipal CustomUserDetails userDetails){
        List<BasketDTO.Response> basketResponseDTO = basketService.selectBasketList(userDetails.getEmail());
        return new ResponseDTO<>().ok(basketResponseDTO,"정상출력 데이터");
    }

    /**
     * 해당 사용자의 장바구니 등록하는 API
     * @param userDetails : 유저정보를 가진 token 객체
     * @param basketRequestDto : 등록할 상품 id를 포함한 DTO
     * @return 등록한 상품정보를 가진 DTO + "성공적으로 추가되었습니다!"
     */
    @Operation(summary = "장바구니 등록")
    @PostMapping("/cart")
    public ResponseDTO<?> addBasket(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody BasketDTO.Request basketRequestDto){
        BasketDTO.Response basketResponseDTO = basketService.insertBasket(userDetails.getEmail(),basketRequestDto);
        return new ResponseDTO<>().ok(basketResponseDTO,"성공적으로 추가되었습니다!");

    }

    /**
     * 해당 사용자의 장바구니 삭제하는 API
     * @param userDetails: 유저정보를 가진 token 객체
     * @param basketId : 삭제할 장바구니 id
     * @return "성공적으로 삭제되었습니다."
     */
    @Operation(summary = "장바구니 삭제")
    @DeleteMapping("/cart")
    public ResponseDTO<?> deleteBasket(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestParam Long basketId){
        String msg = basketService.deleteBasket(userDetails.getEmail(), basketId);
        return new ResponseDTO<>().ok(msg,"성공적으로 삭제되었습니다.");
    }


}
