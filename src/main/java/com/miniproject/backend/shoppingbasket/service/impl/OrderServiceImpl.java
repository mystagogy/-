package com.miniproject.backend.shoppingbasket.service.impl;

import com.miniproject.backend.loanproduct.domain.LoanProduct;
import com.miniproject.backend.loanproduct.service.ProductService;
import com.miniproject.backend.shoppingbasket.domain.Basket;
import com.miniproject.backend.shoppingbasket.dto.BasketDTO;
import com.miniproject.backend.shoppingbasket.exception.BasketException;
import com.miniproject.backend.shoppingbasket.exception.BasketExceptionType;
import com.miniproject.backend.shoppingbasket.repository.BasketRepository;
import com.miniproject.backend.shoppingbasket.service.BasketService;
import com.miniproject.backend.shoppingbasket.service.OrderService;
import com.miniproject.backend.user.domain.User;
import com.miniproject.backend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
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

    /**
     * 장바구니 상품구매
     * @param email : 사용자 email
     * @param basketRequestDto : 상품id를 포함한 DTO
     * @return 구매정보
     */
    @Override
    public BasketDTO.buyResponse buyCart(String email, BasketDTO.Request basketRequestDto) {
        Basket basket = findBasketByUserAndLoanProduct(email,basketRequestDto.getProductId());
        if(basket.getPurchase()==0) {
            LocalDateTime date = LocalDateTime.now();
            Basket basketResult = Basket.builder().id(basket.getId()).user(basket.getUser())
                                                    .loanProduct(basket.getLoanProduct()).purchase(1)
                                                    .orderId(basket.getId()+basket.getUser().getId()+date.format(DateTimeFormatter.BASIC_ISO_DATE))
                                                    .date(date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM))).build();
            basketRepository.save(basketResult);
            return new BasketDTO.buyResponse(basketResult);
        }else{
            throw new BasketException(BasketExceptionType.PURCHASE_DONE);
        }
    }

    /**
     * 사용자정보와 상품정보로 장바구니 찾기
     * @param email : 사용자 email
     * @param productId : 상품id
     * @return 장바구니 정보
     */
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

    /**
     * 구매리스트 출력
     * @param email : 사용자 email
     * @return 구매 리스트
     */
    @Override
    public List<BasketDTO.Response> selectBuyList(String email) {
        User user = userService.findUserByUserId(email);
        List<BasketDTO.Response> basketList = basketRepository.findByUser(user).stream().filter(en->en.getPurchase()==1)
                .map(entity -> new BasketDTO.Response(entity)).collect(Collectors.toList());

        return basketList;
    }

    /**
     * 구매정보 삭제
     * @param email : 사용자 email
     * @param basketId : 장바구니 id
     * @return "성공적으로 삭제되었습니다."
     */
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
