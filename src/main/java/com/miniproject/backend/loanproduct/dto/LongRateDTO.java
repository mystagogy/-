package com.miniproject.backend.loanproduct.dto;

import com.miniproject.backend.loanproduct.domain.LoanRate;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


@Getter
@AllArgsConstructor
@Builder
@Schema(name = "제품에 포함되는 금리 데이터")
public class LongRateDTO {

    private Long id;
    private String rateType;
    private String repayType;
    private float minRate;
    private float maxRate;
    private String mortgageType;
    private Float avgRate;

    public LongRateDTO(LoanRate entity) {
        this.id = entity.getId();
        this.rateType = entity.getRateType();
        this.repayType = entity.getRepayType();
        this.minRate = entity.getMinRate();
        this.maxRate = entity.getMaxRate();
        this.mortgageType = entity.getMortgageType();
        this.avgRate = entity.getAvgRate();
    }

}
