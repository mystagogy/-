package com.miniproject.backend.shoppingbasket.service.impl;

import com.miniproject.backend.loanproduct.domain.LoanProduct;
import com.miniproject.backend.loanproduct.service.ProductService;
import com.miniproject.backend.shoppingbasket.domain.Basket;
import com.miniproject.backend.shoppingbasket.dto.BasketResponseDTO;
import com.miniproject.backend.shoppingbasket.exception.BasketException;
import com.miniproject.backend.shoppingbasket.exception.BasketExceptionType;
import com.miniproject.backend.shoppingbasket.repository.BasketRepository;
import com.miniproject.backend.shoppingbasket.service.BasketService;
import com.miniproject.backend.shoppingbasket.service.OrderService;
import com.miniproject.backend.user.domain.User;
import com.miniproject.backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final BasketRepository basketRepository;
    private final ProductService productService;
    private final UserService userService;

    private final BasketService basketService;
    @Override
    public BasketResponseDTO buyCart(String email, String productId) {
        Basket basket = findBasketByUserAndLoanProduct(email,productId);
        if(basket.getPurchase()==0) {
            Basket basketResult = Basket.builder().id(basket.getId()).user(basket.getUser()).loanProduct(basket.getLoanProduct()).purchase(1).build();
            basketRepository.save(basketResult);
            return new BasketResponseDTO(basketResult);
        }else{
            throw new BasketException(BasketExceptionType.PURCHASE_DONE);
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
        throw new BasketException(BasketExceptionType.NOT_EXIST_BASKET);

    }

    @Override
    public List<BasketResponseDTO> selectBuyList(String email) {
        User user = userService.findUserByUserId(email);
        List<BasketResponseDTO> basketList = basketRepository.findByUser(user).stream().filter(en->en.getPurchase()==1)
                .map(entity -> new BasketResponseDTO(entity)).collect(Collectors.toList());

        return basketList;
    }

    @Override
    public String deleteBuy(String email, Long basketId) {

        Basket basket = basketService.findBasketByIdAndUser(email,basketId);

        if (basket.getPurchase()==1) {
            basketRepository.delete(basket);
            return "성공적으로 삭제되었습니다.";
        }else{
            throw new BasketException(BasketExceptionType.NOT_PURCHASE);
        }

    }
}
