package com.miniproject.backend.like.service;

import com.miniproject.backend.global.jwt.CustomUserDetails;
import com.miniproject.backend.like.dto.LikeRequestDto;
import com.miniproject.backend.like.dto.LikeResponseDto;
import com.miniproject.backend.user.domain.User;

import java.util.List;

public interface LikeService {
    LikeResponseDto addLike(String email, String productId);

    List<LikeResponseDto> selectAllLike(String email);

    List<LikeResponseDto> deleteLike(String userEmail, long Id);

}
