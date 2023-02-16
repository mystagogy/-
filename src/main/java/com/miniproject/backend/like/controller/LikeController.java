package com.miniproject.backend.like.controller;

import com.miniproject.backend.global.dto.ResponseDTO;
import com.miniproject.backend.like.dto.LikeRequestDto;
import com.miniproject.backend.like.dto.LikeResponseDto;
import com.miniproject.backend.like.service.LikeServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LikeController {

    private final LikeServiceImpl likeService;

    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "관심상품등록")
    @PostMapping("/like")
    public ResponseDTO<?> insertLike(@RequestBody LikeRequestDto likeRequestDto) {

        LikeResponseDto likeResponseDto =likeService.addLike(likeRequestDto);
        return new ResponseDTO<>().ok(likeResponseDto, "정상출력");

    }


    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "관심상품리스트출력")
    @GetMapping("/like")
    public ResponseDTO<?> selectAllLike(@RequestParam String userEmail){
        List<LikeResponseDto> likeResponseDtoList = likeService.selectAllLike(userEmail);
        return new ResponseDTO<>().ok(likeResponseDtoList, "정상출력");
    }

    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "관심상품삭제")
    @DeleteMapping("/like")
    public ResponseDTO<?> deleteLike(@RequestParam String userEmail, @RequestParam Long Id){

        List<LikeResponseDto> likeResponseDtoList = likeService.deleteLike(userEmail,Id);
        return new ResponseDTO<>().ok(likeResponseDtoList, "정상출력");
    }

}
