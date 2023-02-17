package com.miniproject.backend.loanproduct.dto;

import com.miniproject.backend.loanproduct.domain.LoanProduct;
import com.miniproject.backend.loanproduct.domain.LoanRate;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ProductDetailDTO {

    @Schema(name = "상품 id")
    private String ProductId;
    @Schema(name = "카테고리 이름")
    private String categoryName;
    @Schema(name = "은행 이름")
    private String bankName;
    @Schema(name = "상품 이름")
    private String productName;
    @Schema(name = "가입 방법")
    private String joinWay;
    @Schema(name = "대출부대비용")
    private String loanIncidentalExpenses;
    @Schema(name = "중도상환수수료")
    private String earlyRepayFee;
    @Schema(name = "연체이자율")
    private String delayRate;
    @Schema(name = "대출한도")
    private String loanLimit;
    @Schema(name = "공시 시작일")
    private String disclosureStartDay;
    @Schema(name = "공시 종료일")
    private String disclosureEndDay;
    private List<LongRateDTO> longRateDTO;

    public ProductDetailDTO(LoanProduct loanProduct) {
        this.ProductId = loanProduct.getId();
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
        this.longRateDTO = toRateDTO(loanProduct.getLoanRates());
    }

    public List<LongRateDTO> toRateDTO(List<LoanRate> loanRates) {
        return loanRates.stream().map(LongRateDTO::new)
                .collect(Collectors.toList());
    }
}
