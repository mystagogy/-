package com.miniproject.backend.loanproduct.dto;

import com.miniproject.backend.loanproduct.domain.LoanProduct;
import com.miniproject.backend.loanproduct.domain.LoanRate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

public class ProductDto {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
        private String productId;
        private String productName;
        private String bankName;
        private String bankImgPath;
        private String loanLimit;
        private List<LoanRate> loanRateList;

        public Response(LoanProduct loanProduct) {
            this.productId = loanProduct.getId();
            this.productName = loanProduct.getProductNm();
            this.bankName = loanProduct.getBank().getBankNm();
            this.bankImgPath = loanProduct.getBank().getImgPath();
            this.loanLimit = loanProduct.getLoanLimit();
            this.loanRateList = loanProduct.getLoanRates();
        }
    }

    @AllArgsConstructor
    @Getter
    @NoArgsConstructor
    public static class DetailResponse {
        private String productId;
        private String categoryName;
        private String bankName;
        private String productName;
        private String joinWay;
        private String loanIncidentalExpenses;
        private String earlyRepayFee;
        private String delayRate;
        private String loanLimit;
        private String disclosureStartDay;
        private String disclosureEndDay;
        private List<LoanRate> loanRateList;

        public DetailResponse(LoanProduct loanProduct) {
            this.productId = loanProduct.getId();
            this.categoryName = loanProduct.getCategory().getCategoryName();
            this.bankName = loanProduct.getBank().getBankNm();
            this.productName = loanProduct.getProductNm();
            this.joinWay = loanProduct.getJoinWay();
            this.loanIncidentalExpenses = loanProduct.getLoanIncidentalExpenses();
            this.earlyRepayFee = loanProduct.getEarlyRepayFee();
            this.delayRate = loanProduct.getDelayRate();
            this.loanLimit = loanProduct.getLoanLimit();
            this.disclosureStartDay = loanProduct.getDisclosureStartDay();
            this.disclosureEndDay = loanProduct.getDisclosureEndDay();
            this.loanRateList = loanProduct.getLoanRates();
        }
    }

}
