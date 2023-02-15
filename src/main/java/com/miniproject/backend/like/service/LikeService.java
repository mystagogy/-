package com.miniproject.backend.like.service;

import com.miniproject.backend.like.dto.LikeResponseDto;

public interface LikeService {
    public LikeResponseDto addLike(String productId, String userEmail);
}
