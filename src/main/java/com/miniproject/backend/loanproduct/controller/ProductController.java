package com.miniproject.backend.loanproduct.controller;

import com.miniproject.backend.global.dto.ResponseDTO;
import com.miniproject.backend.loanproduct.dto.ProductDetailDTO;
import com.miniproject.backend.loanproduct.dto.ProductListDTO;
import com.miniproject.backend.loanproduct.service.ProductService;
import com.miniproject.backend.loanproduct.dto.SearchResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "상품 리스트 api")
    @GetMapping("/products")

    public ResponseDTO<?> ProductList() {
        List<ProductListDTO> productListDTO = productService.selectProductList();
        return new ResponseDTO<>().ok(productListDTO, "정상 출력");
    }

    @Operation(summary = "상품 상세정보 ")
    @GetMapping("/product/{id}")
    public ResponseDTO<?> ProductDetail(@PathVariable("id") String productId) {
        ProductDetailDTO productDetailDTO = productService.findById(productId);
        return new ResponseDTO<>().ok(productDetailDTO,"정상 출력");
    }


    @Operation(summary = "상품검색")
    @GetMapping("/search")
    public ResponseDTO<?> searchProduct(@RequestParam String keyword) {
        List<SearchResponseDto> searchResponseDtoList = productService.searchList(keyword);
        return new ResponseDTO<>().ok(searchResponseDtoList,"정상 출력");

    }

}
