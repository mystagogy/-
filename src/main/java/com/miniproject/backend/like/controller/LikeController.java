package com.miniproject.backend.like.controller;

import com.miniproject.backend.global.dto.ResponseDTO;
import com.miniproject.backend.global.jwt.CustomUserDetails;
import com.miniproject.backend.like.dto.LikeDto;
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

    /**
     * 사용자가 선택한 관심상품을 등록 API
     * @param userDetails : 인증된 유저정보와 token 타입을 가진 객체
     * @param request : 관심상품을 등록하고자 하는 상품 ID
     * @return 등록된 상품관련정보와 관심상품 ID를 가진 DTO, 상품등록 성공 메세지
     */
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "관심상품등록")
    @PostMapping("/like")
    public ResponseDTO<?> insertLike(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody LikeDto.InsertRequest request) {

        LikeDto.Response likeResponseDto =likeService.addLike(userDetails.getEmail(),request.getProductId());
        return new ResponseDTO<>().ok(likeResponseDto, "성공적으로 추가되었습니다.");

    }

    /**
     * 사용자가 등록한 관심상품리스트 출력 API
     * @param userDetails : 인증된 유저정보와 token 타입을 가진 객체
     * @return 사용자가 등록한 모든 관심상품 정보와 ID를 가진 DTO List, 정상 출력 메세지
     */
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "관심상품리스트출력")
    @GetMapping("/like")
    public ResponseDTO<?> selectAllLike(@AuthenticationPrincipal CustomUserDetails userDetails){

        List<LikeDto.Response> likeResponseDtoList = likeService.selectAllLike(userDetails.getEmail());
        return new ResponseDTO<>().ok(likeResponseDtoList, "정상출력");
    }

    /**
     * 관심상품 등록 해제 API
     * @param userDetails : 인증된 유저정보와 token 타입을 가진 객체
     * @param request : 삭제하고자 하는 관심상품 ID DTO
     * @return : 삭제 성공 메세지
     */
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "관심상품삭제")
    @DeleteMapping("/like")
    public ResponseDTO<?> deleteLike(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody LikeDto.DeleteRequest request){
        likeService.deleteLike(userDetails.getEmail(), request.getId());
        return new ResponseDTO<>().ok(null, "성공적으로 삭제되었습니다.");
    }

}
