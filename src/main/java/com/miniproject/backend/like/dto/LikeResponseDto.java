package com.miniproject.backend.like.dto;

import com.miniproject.backend.bank.Bank;
import com.miniproject.backend.like.domain.Like;
import com.miniproject.backend.loanproduct.domain.LoanProduct;
import com.miniproject.backend.loanproduct.domain.LoanRate;
import com.miniproject.backend.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Schema(name = "관심상품 정보 출력 데이터")
public class LikeResponseDto {

    private Long likeId;
    private String bankImgPath;
    private String bankName;
    private String productName;
    private List<LoanRate> loanRateList;
    private String loanLimit;

    public  LikeResponseDto(Like like){
        this.likeId = like.getId();
        this.bankImgPath = like.getLoanProduct().getBank().getImgPath();
        this.bankName = like.getLoanProduct().getBank().getBankNm();
        this.productName = like.getLoanProduct().getProductNm();
        this.loanRateList = like.getLoanProduct().getLoanRates();
        this.loanLimit = like.getLoanProduct().getLoanLimit();
    }


}
