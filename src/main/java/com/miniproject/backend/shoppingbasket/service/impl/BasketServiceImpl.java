package com.miniproject.backend.shoppingbasket.service.impl;

import com.miniproject.backend.loanproduct.domain.LoanProduct;
import com.miniproject.backend.loanproduct.repository.ProductRepository;
import com.miniproject.backend.shoppingbasket.domain.Basket;
import com.miniproject.backend.shoppingbasket.dto.BasketResponseDTO;
import com.miniproject.backend.shoppingbasket.repository.BasketRepository;
import com.miniproject.backend.shoppingbasket.service.BasketService;
import com.miniproject.backend.user.domain.User;
import com.miniproject.backend.user.repository.UserRepository;
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
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Override
    public List<BasketResponseDTO> selectBasketList(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            return basketRepository.findById(userOptional.get().getId())
                    .stream()
                    .map(entity -> new BasketResponseDTO(entity)).collect(Collectors.toList());

        }
        //TODO 예외처리
        return null;
    }

    @Override
    public BasketResponseDTO insertBasket(String email, String productId) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        Optional<LoanProduct> loanProductOptional = productRepository.findById(productId);

        if (!basketRepository.existsByUserAndLoanProduct(userOptional.get(),loanProductOptional.get())) {
            Basket basketResult = Basket.builder()
                    .user(userOptional.get())
                    .loanProduct(loanProductOptional.get())
                    .build();

            basketRepository.save(basketResult);
            return new BasketResponseDTO(basketResult);
        } else {
            // TODO 장바구니에 이미 존재할 때 예외처리
            return null;
        }
    }

    @Transactional
    @Override
    public String deleteBasket(String email, String productId) {

        Optional<User> userOptional = userRepository.findByEmail(email);
        Optional<LoanProduct> loanProductOptional = productRepository.findById(productId);

        if (basketRepository.existsByUserAndLoanProduct(userOptional.get(),loanProductOptional.get())) {
            basketRepository.deleteByUserAndLoanProduct(userOptional.get(), loanProductOptional.get());
            return "성공적으로 삭제되었습니다.";
        }else{
           //TODO 장바구니에 없을 때 예외처리
           return null;
       }


    }



}
