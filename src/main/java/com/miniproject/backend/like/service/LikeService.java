package com.miniproject.backend.like.service;


import com.miniproject.backend.like.dto.LikeDto;


import java.util.List;

public interface LikeService {
    LikeDto.Response addLike(String email, String productId);

    List<LikeDto.Response> selectAllLike(String email);

    void deleteLike(String userEmail, long Id);

}
