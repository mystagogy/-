package com.miniproject.backend.like.service;

import com.miniproject.backend.like.domain.Like;
import com.miniproject.backend.like.dto.LikeRequestDto;
import com.miniproject.backend.like.dto.LikeResponseDto;
import com.miniproject.backend.like.exception.LikeException;
import com.miniproject.backend.like.exception.LikeExceptionType;
import com.miniproject.backend.like.repository.LikeRepository;
import com.miniproject.backend.loanproduct.domain.LoanProduct;
import com.miniproject.backend.loanproduct.service.ProductService;
import com.miniproject.backend.shoppingbasket.dto.BasketResponseDTO;
import com.miniproject.backend.user.domain.User;
import com.miniproject.backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService{

    private final LikeRepository likeRepository;
    private final UserService userService;
    private final ProductService productService;

    public LikeResponseDto addLike(LikeRequestDto likeRequestDto) {

        User user = userService.findUserByUserId(likeRequestDto.getUserEamil());
        LoanProduct loanProduct = productService.findProductByProductId(likeRequestDto.getProductId());

        if(!likeRepository.existsByUserAndLoanProduct(user,loanProduct)){
            Like like = Like.builder()
                    .loanProduct(loanProduct)
                    .user(user)
                    .build();
            likeRepository.save(like);

            LikeResponseDto likeResponseDto = LikeResponseDto.builder()
                    .likeId(like.getId())
                    .bankImgPath(like.getLoanProduct().getBank().getImgPath())
                    .bankName(like.getLoanProduct().getBank().getBankNm())
                    .productName(like.getLoanProduct().getProductNm())
                    .loanRateList(like.getLoanProduct().getLoanRates())
                    .loanLimit(like.getLoanProduct().getLoanLimit())
                    .build();
            return likeResponseDto;
        }else{
            throw new LikeException(LikeExceptionType.EXIST_LIKE);
        }
    }

    @Override
    public List<LikeResponseDto> selectAllLike(String userEmail) {
        User user = userService.findUserByUserId(userEmail);
        List<Like> likes = likeRepository.findLikesByUser(user);
        List<LikeResponseDto> likeList = new ArrayList<>();
        for(Like like : likes){
            likeList.add(LikeResponseDto.builder()
                    .likeId(like.getId())
                    .bankImgPath(like.getLoanProduct().getBank().getImgPath())
                    .bankName(like.getLoanProduct().getBank().getBankNm())
                    .productName(like.getLoanProduct().getProductNm())
                    .loanRateList(like.getLoanProduct().getLoanRates())
                    .loanLimit(like.getLoanProduct().getLoanLimit())
                    .build());
        }
        return likeList;

    }
}
