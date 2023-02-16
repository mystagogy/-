package com.miniproject.backend.like.service;

import com.miniproject.backend.like.dto.LikeRequestDto;
import com.miniproject.backend.like.dto.LikeResponseDto;

import java.util.List;

public interface LikeService {
    LikeResponseDto addLike(LikeRequestDto likeRequestDto);

    List<LikeResponseDto> selectAllLike(String userEmail);
}
