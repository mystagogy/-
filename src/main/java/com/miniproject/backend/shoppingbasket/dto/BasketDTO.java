package com.miniproject.backend.shoppingbasket.dto;

import com.miniproject.backend.loanproduct.domain.LoanRate;
import com.miniproject.backend.shoppingbasket.domain.Basket;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

public class BasketDTO {

    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Builder
    @Setter
    @Schema(name = "장바구니 입력 요청 데이터")
    public static class Request {

        private String userEmail;
        private String productId;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {

        @Schema(name = "장바구니 id")
        private Long cartId;

        @Schema(name = "상품 id")
        private String productId;

        @Schema(name = "은행이름")
        private String bankName;

        @Schema(name = "은행 아이콘")
        private String bankImgPath;

        @Schema(name = "대출 상품 이름")
        private String productName;

        @Schema(name = "한도")
        private String loanLimit;

        @Schema(name = "대출금리 리스트")
        private List<LoanRate> loanRateList;

        public Response(Basket basket){
            this.cartId = basket.getId();
            this.productId = basket.getLoanProduct().getId();
            this.bankName = basket.getLoanProduct().getBank().getBankNm();
            this.bankImgPath = basket.getLoanProduct().getBank().getImgPath();
            this.productName = basket.getLoanProduct().getProductNm();
            this.loanLimit = basket.getLoanProduct().getLoanLimit();
            this.loanRateList = basket.getLoanProduct().getLoanRates();

        }
    }
}
