package com.miniproject.backend.loanproduct.controller;

import com.miniproject.backend.global.dto.ResponseDTO;
import com.miniproject.backend.loanproduct.dto.PagingDTO;
import com.miniproject.backend.loanproduct.dto.ProductDto;
import com.miniproject.backend.loanproduct.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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
    @GetMapping("/product")

    public ResponseDTO<?> ProductList() {
        List<ProductDto.Response> productListDTO = productService.selectProductList();
        return new ResponseDTO<>().ok(productListDTO, "정상 출력");
    }

    @Operation(summary = "상품 상세정보 ")
    @GetMapping("/product/{id}")
    public ResponseDTO<?> ProductDetail(@PathVariable("id") String productId) {
        ProductDto.DetailResponse productDetail = productService.findById(productId);
        return new ResponseDTO<>().ok(productDetail, "정상 출력");
    }


    @Operation(summary = "상품검색")
    @GetMapping("/search")
    public ResponseDTO<?> searchProduct(@RequestParam String keyword, @PageableDefault(size = 8) Pageable pageable) {
        PagingDTO pagingDTO = productService.searchList(keyword, pageable);
        return new ResponseDTO<>().ok(pagingDTO, "정상 출력");

    }

}
