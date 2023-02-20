package com.miniproject.backend.like.controller;

import com.miniproject.backend.global.dto.ResponseDTO;
import com.miniproject.backend.global.jwt.CustomUserDetails;
import com.miniproject.backend.like.dto.LikeRequestDto;
import com.miniproject.backend.like.dto.LikeResponseDto;
import com.miniproject.backend.like.service.LikeServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LikeController {

    private final LikeServiceImpl likeService;


    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "관심상품등록")
    @PostMapping("/like")
    public ResponseDTO<?> insertLike(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody LikeRequestDto likeRequestDto) {

        LikeResponseDto likeResponseDto =likeService.addLike(userDetails.getEmail(),likeRequestDto.getProductId());
        return new ResponseDTO<>().ok(likeResponseDto, "성공적으로 추가되었습니다.");

    }


    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "관심상품리스트출력")
    @GetMapping("/like")
    public ResponseDTO<?> selectAllLike(@AuthenticationPrincipal CustomUserDetails userDetails){

        List<LikeResponseDto> likeResponseDtoList = likeService.selectAllLike(userDetails.getEmail());
        return new ResponseDTO<>().ok(likeResponseDtoList, "정상출력");
    }

    /**
     *
     * @param userDetails
     * @param Id
     * @return
     */
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "관심상품삭제")
    @DeleteMapping("/like")
    public ResponseDTO<?> deleteLike(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestParam Long Id){

        List<LikeResponseDto> likeResponseDtoList = likeService.deleteLike(userDetails.getEmail(), Id);
        return new ResponseDTO<>().ok(likeResponseDtoList, "성공적으로 삭제되었습니다.");
    }

}
