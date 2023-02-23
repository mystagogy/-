package com.miniproject.backend.like.service;

import com.miniproject.backend.like.domain.Like;
import com.miniproject.backend.like.dto.LikeDto;
import com.miniproject.backend.like.exception.LikeException;
import com.miniproject.backend.like.exception.LikeExceptionType;
import com.miniproject.backend.like.repository.LikeRepository;
import com.miniproject.backend.loanproduct.domain.LoanProduct;
import com.miniproject.backend.loanproduct.service.ProductService;
import com.miniproject.backend.user.domain.User;
import com.miniproject.backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService{

    private final LikeRepository likeRepository;
    private final UserService userService;
    private final ProductService productService;


    @Override
    public LikeDto.Response addLike(String email, String productId) {

        User user = userService.findUserByUserId(email);
        LoanProduct loanProduct = productService.findProductByProductId(productId);

        if(!likeRepository.existsByUserAndLoanProduct(user,loanProduct)){
            Like like = Like.builder()
                    .loanProduct(loanProduct)
                    .user(user)
                    .build();
            likeRepository.save(like);

            LikeDto.Response responseDto = new LikeDto.Response(like);
            return responseDto;
        }else{
            throw new LikeException(LikeExceptionType.EXIST_LIKE);
        }
    }

    @Override
    public List<LikeDto.Response> selectAllLike(String email) {
        User user = userService.findUserByUserId(email);
        List<Like> likes = likeRepository.findLikesByUser(user);
        List<LikeDto.Response> likeList = likes.stream().map(like -> new LikeDto.Response(like)).collect(Collectors.toList());
        if(!likeList.isEmpty()) {
            return likeList;
        }
        else throw new LikeException(LikeExceptionType.EMPTY_LIKE_LIST);
    }

    @Override
    public void deleteLike(String email, long Id) {
        User user = userService.findUserByUserId(email);
        Like like = likeRepository.findLikeByIdAndUser(Id, user);
        if(like != null){
            likeRepository.delete(like);
        }else{
            throw new LikeException(LikeExceptionType.NOT_EXIST_LIKE);
        }
    }
}
