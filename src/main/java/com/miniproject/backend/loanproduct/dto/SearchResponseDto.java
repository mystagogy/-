package com.miniproject.backend.loanproduct.dto;

import com.miniproject.backend.loanproduct.domain.LoanRate;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Schema(name = "검색상품 정보 출력 데이터")
public class SearchResponseDto {

    private String productId;
    private String bankImgPath;
    private String bankName;
    private String productName;
    private List<LoanRate> loanRateList;
    private String loanLimit;

}
