package com.miniproject.backend.shoppingbasket.service.impl;

import com.miniproject.backend.loanproduct.domain.LoanProduct;
import com.miniproject.backend.loanproduct.service.ProductService;
import com.miniproject.backend.shoppingbasket.domain.Basket;
import com.miniproject.backend.shoppingbasket.dto.BasketResponseDTO;
import com.miniproject.backend.shoppingbasket.repository.BasketRepository;
import com.miniproject.backend.shoppingbasket.service.OrderService;
import com.miniproject.backend.user.domain.User;
import com.miniproject.backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final BasketRepository basketRepository;
    private final ProductService productService;
    private final UserService userService;
    @Override
    public BasketResponseDTO buyCart(String email, String productId) {
        Basket basket = findBasketByUserAndLoanProduct(email,productId);
        if(basket.getPurchase()==0) {
            Basket basketResult = Basket.builder().id(basket.getId()).user(basket.getUser()).loanProduct(basket.getLoanProduct()).purchase(1).build();
            basketRepository.save(basketResult);
            return new BasketResponseDTO(basketResult);
        }else{
            //TODO 이미 구매한것에 대한 예외처리
            return null;

        }
    }

    @Override
    public Basket findBasketByUserAndLoanProduct(String email, String productId) {
        User user = userService.findUserByUserId(email);
        LoanProduct loanProduct = productService.findProductByProductId(productId);
        Optional<Basket> basketOptional = basketRepository.findByUserAndLoanProduct(user,loanProduct);

        if(basketOptional.isPresent()){
            Basket basket = basketOptional.get();
            return basket;
        }
        //TODO 해당 장바구니 상품 없을시 예외처리
        return null;

    }
}
