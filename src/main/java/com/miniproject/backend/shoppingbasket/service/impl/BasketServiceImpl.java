package com.miniproject.backend.shoppingbasket.service.impl;

import com.miniproject.backend.loanproduct.domain.LoanProduct;
import com.miniproject.backend.loanproduct.repository.ProductRepository;
import com.miniproject.backend.loanproduct.service.ProductService;
import com.miniproject.backend.shoppingbasket.domain.Basket;
import com.miniproject.backend.shoppingbasket.dto.BasketResponseDTO;
import com.miniproject.backend.shoppingbasket.exception.BasketException;
import com.miniproject.backend.shoppingbasket.exception.BasketExceptionType;
import com.miniproject.backend.shoppingbasket.repository.BasketRepository;
import com.miniproject.backend.shoppingbasket.service.BasketService;
import com.miniproject.backend.user.domain.User;
import com.miniproject.backend.user.repository.UserRepository;
import com.miniproject.backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BasketServiceImpl implements BasketService {

    private final BasketRepository basketRepository;
    private final ProductService productService;
    private final UserService userService;

    @Override
    public List<BasketResponseDTO> selectBasketList(String email) {
        User user = userService.findUserByUserId(email);
            return basketRepository.findById(user.getId())
                    .stream()
                    .map(entity -> new BasketResponseDTO(entity)).collect(Collectors.toList());

    }

    @Override
    public BasketResponseDTO insertBasket(String email, String productId) {
        User user = userService.findUserByUserId(email);
        LoanProduct loanProduct = productService.findProductByProductId(productId);

        if (!basketRepository.existsByUserAndLoanProduct(user,loanProduct)){
            Basket basketResult = Basket.builder()
                    .user(user)
                    .loanProduct(loanProduct)
                    .build();

            basketRepository.save(basketResult);
            return new BasketResponseDTO(basketResult);
        } else {
            throw new BasketException(BasketExceptionType.EXIST_BASKET);
        }
    }

    @Transactional
    @Override
    public String deleteBasket(String email, String productId) {

        User user = userService.findUserByUserId(email);
        LoanProduct loanProduct = productService.findProductByProductId(productId);

        if (basketRepository.existsByUserAndLoanProduct(user,loanProduct)) {
            basketRepository.deleteByUserAndLoanProduct(user, loanProduct);
            return "성공적으로 삭제되었습니다.";
        }else{
           throw new BasketException(BasketExceptionType.NOT_EXIST_BASKET);
       }


    }



}
