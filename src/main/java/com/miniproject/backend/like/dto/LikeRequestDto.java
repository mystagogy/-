package com.miniproject.backend.like.dto;

import com.miniproject.backend.like.domain.Like;
import com.miniproject.backend.loanproduct.domain.LoanProduct;
import com.miniproject.backend.user.domain.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@Schema(name = "관심상품 요청 데이터")
public class LikeRequestDto {
    private LoanProduct loanProduct;
    private User user;

    public Like toEntity(){
        return Like.builder()
                .loanProduct(this.loanProduct)
                .user(this.user)
                .build();
    }
}
