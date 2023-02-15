package com.miniproject.backend.like.controller;

import com.miniproject.backend.global.dto.ResponseDTO;
import com.miniproject.backend.like.dto.LikeResponseDto;
import com.miniproject.backend.like.service.LikeServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class LikeController {

    private final LikeServiceImpl likeService;

    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "관심상품등록")
    @PostMapping("/like")
    public ResponseDTO<?> insertLike(@RequestParam String productId, @RequestParam String userEmail) {

        LikeResponseDto likeResponseDto =likeService.addLike(productId, userEmail);
        if(likeResponseDto != null){
            return new ResponseDTO<>().ok(likeResponseDto, "정상출력");
        }
        return null;
    }
}
