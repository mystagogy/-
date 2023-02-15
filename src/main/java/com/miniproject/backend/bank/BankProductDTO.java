//package com.miniproject.backend.bank;
//
//import io.swagger.v3.oas.annotations.media.Schema;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Getter;
//
//@Getter
//@AllArgsConstructor
//@Builder
//@Schema(name = "제품리스트에 포함되는 은행 데이터")
//public class BankProductDTO {
//
//    @Schema(description = "은행이름", defaultValue = "test은행")
//    private String bankNm;
//    @Schema(description = "은행 img")
//    private String imgPath;
//
//    public BankProductDTO(Bank entity) {
//        this.bankNm = entity.getBankNm();
//        this.imgPath = entity.getImgPath();
//    }
//
//
//}
