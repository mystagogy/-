package com.miniproject.backend.loanproduct.dto;

import com.miniproject.backend.bank.Bank;
import com.miniproject.backend.bank.BankProductDTO;
import com.miniproject.backend.loanproduct.domain.LoanProduct;
import com.miniproject.backend.loanproduct.domain.LoanRate;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Builder
@Getter
@ToString
@Schema(name = "제품 리스트 출력 데이터 ")
public class ProductListDTO {

    @Schema(description = "상품아이디", defaultValue = "1")
    private Long id;
    @Schema(description = "상품 이름", defaultValue = "test대출")
    private String productNm;

    private BankProductDTO bankProductDTO;
    private List<LongRateShortDTO> longRateShortDTO;


    public ProductListDTO(LoanProduct entity) {
        this.id = Long.valueOf(entity.getId());
        this.productNm = entity.getProductNm();
        this.bankProductDTO = toBankDTO(entity.getBank());
        this.longRateShortDTO = toRateDTO(entity.getLoanRates());
    }

    public BankProductDTO toBankDTO(Bank bank) {
        return BankProductDTO.builder()
                .bankNm(bank.getBankNm())
                .imgPath(bank.getImgPath())
                .build();

    }

    public List<LongRateShortDTO> toRateDTO(List<LoanRate> loanRates) {
        return loanRates.stream().map(LongRateShortDTO::new)
                .collect(Collectors.toList());
    }
}
