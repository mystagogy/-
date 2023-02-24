package com.miniproject.backend.shoppingbasket.service.impl;

import com.miniproject.backend.loanproduct.domain.LoanProduct;
import com.miniproject.backend.loanproduct.service.ProductService;
import com.miniproject.backend.shoppingbasket.domain.Basket;
import com.miniproject.backend.shoppingbasket.dto.BasketDTO;
import com.miniproject.backend.shoppingbasket.exception.BasketException;
import com.miniproject.backend.shoppingbasket.exception.BasketExceptionType;
import com.miniproject.backend.shoppingbasket.repository.BasketRepository;
import com.miniproject.backend.shoppingbasket.service.BasketService;
import com.miniproject.backend.user.domain.User;
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

    /**
     * 장바구니 리스트 출력
     * @param email : 사용자 email
     * @return 등록된 장바구니 리스트
     */
    @Override
    public List<BasketDTO.Response> selectBasketList(String email) {
        User user = userService.findUserByUserId(email);
        List<BasketDTO.Response> basketList = basketRepository.findByUser(user).stream().filter(en->en.getPurchase()==0)
                .map(entity -> new BasketDTO.Response(entity)).collect(Collectors.toList());

        return basketList;

    }

    /**
     * 장바구니 등록
     * @param email : 사용자 email
     * @param basketRequestDto : 상품id를 포함한 DTO
     * @return 등록 장바구니 정보
     */
    @Override
    public BasketDTO.Response insertBasket(String email,BasketDTO.Request basketRequestDto) {
        User user = userService.findUserByUserId(email);
        LoanProduct loanProduct = productService.findProductByProductId(basketRequestDto.getProductId());

        if (!basketRepository.existsByUserAndLoanProduct(user,loanProduct)){
            Basket basketResult = Basket.builder()
                    .user(user)
                    .loanProduct(loanProduct)
                    .build();

            basketRepository.save(basketResult);
            return new BasketDTO.Response(basketResult);
        } else {
            throw new BasketException(BasketExceptionType.EXIST_BASKET);
        }
    }

    /**
     * 장바구니 삭제
     * @param email : 사용자 email
     * @param basketId : 삭제할 장바구니 id
     * @return "성공적으로 삭제되었습니다."
     */
    @Transactional
    @Override
    public String deleteBasket(String email, Long basketId) {

        Basket basket = findBasketByIdAndUser(email,basketId);

        if (basket.getPurchase()==0) {
            basketRepository.delete(basket);
            return "성공적으로 삭제되었습니다.";
        }else{
            throw new BasketException(BasketExceptionType.NOT_EXIST_BASKET);
        }


    }

    /**
     * 장바구니id와 사용자정보로 장바구니 찾기
     * @param email : 사용자 email
     * @param basketId : 장바구니 id
     * @return 장바구니 정보
     */
    @Override
    public Basket findBasketByIdAndUser(String email, Long basketId) {
        User user = userService.findUserByUserId(email);
        Optional<Basket> basketOptional = basketRepository.findByIdAndUser(basketId,user);

        if (basketOptional.isPresent()) {
            Basket basket = basketOptional.get();
            return basket;
        }else{
            throw new BasketException(BasketExceptionType.NOT_EXIST_BASKET);
        }
    }


}
