package com.miniproject.backend.like.dto;

import com.miniproject.backend.like.domain.Like;
import com.miniproject.backend.loanproduct.domain.LoanRate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

public class LikeDto {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class InsertRequest{
        String productId;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DeleteRequest{
        private Long Id;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response{
        private Long likeId;
        private String productId;
        private String productType;
        private String bankImgPath;
        private String bankName;
        private String productName;
        private List<LoanRate> loanRateList;
        private String loanLimit;

        public Response(Like like){
            this.likeId = like.getId();
            this.productId = like.getLoanProduct().getId();
            this.productType = like.getLoanProduct().getCategory().getCategoryName();
            this.bankImgPath = like.getLoanProduct().getBank().getImgPath();
            this.bankName = like.getLoanProduct().getBank().getBankNm();
            this.productName = like.getLoanProduct().getProductNm();
            this.loanRateList = like.getLoanProduct().getLoanRates();
            this.loanLimit = like.getLoanProduct().getLoanLimit();
        }
    }
}
