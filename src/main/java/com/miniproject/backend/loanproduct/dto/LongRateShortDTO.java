package com.miniproject.backend.loanproduct.dto;

import com.miniproject.backend.loanproduct.domain.LoanRate;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


@Getter
@AllArgsConstructor
@Builder
@Schema(name = "제품리스트에 포함되는 금리 데이터")
public class LongRateShortDTO {


    @Schema(description = "평균 금리", defaultValue = "1.11")
    private Float avgRate;

    public LongRateShortDTO(LoanRate entity) {
        this.avgRate = entity.getAvgRate();
    }

}
