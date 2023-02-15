package com.miniproject.backend.loanproduct.controller;

import com.miniproject.backend.global.dto.ResponseDTO;
import com.miniproject.backend.loanproduct.dto.ProductListDTO;
import com.miniproject.backend.loanproduct.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "상품 리스트 api")
    @GetMapping("/product")

    public ResponseDTO<?> ProductList() {
        List<ProductListDTO> productListDTO = productService.selectProductList();
        return new ResponseDTO<>().ok(productListDTO, "정상 출력");
    }

}
