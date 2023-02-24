package com.miniproject.backend.loanproduct.service.impl;

import com.miniproject.backend.loanproduct.domain.LoanProduct;
import com.miniproject.backend.loanproduct.dto.*;
import com.miniproject.backend.loanproduct.exception.ProductException;
import com.miniproject.backend.loanproduct.exception.ProductExceptionType;
import com.miniproject.backend.loanproduct.repository.ProductRepository;
import com.miniproject.backend.loanproduct.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<ProductDto.Response> selectProductList() {
        List<LoanProduct> productsList = productRepository.findAll();

        return productsList.stream()
                .map(ProductDto.Response::new)
                .collect(Collectors.toList());
    }

    /**
     * 상품 ID로 상품 찾기
     * @param productId : 상품 ID
     * @return 상품 정보
     */
    @Override
    public LoanProduct findProductByProductId(String productId) {
        Optional<LoanProduct> loanProduct = productRepository.findById(productId);
        if (!loanProduct.isEmpty()) {
            return loanProduct.get();
        } else {
            throw new ProductException(ProductExceptionType.PRODUCT_NOT_EXIST);
        }
    }

    @Override
    public ProductDto.DetailResponse findById(String productId) {
        LoanProduct loanProduct = productRepository.findById(productId).orElseThrow(() -> new ProductException(ProductExceptionType.PRODUCT_NOT_EXIST));
        return new ProductDto.DetailResponse(loanProduct);
    }


    /**
     * 상품명에서 검색키워드를 포함하는 상품 찾기
     * @param keyword : 검색 키워드
     * @return : 검색 키워드를 포함하는 상품 리스트
     */
    @Override
    public List<ProductDto.SearchResponseDto> searchList(String keyword) {

        List<LoanProduct> products = productRepository.findByProductNmContaining(keyword);
        List<ProductDto.SearchResponseDto> searchList = products.stream().map(product -> new ProductDto.SearchResponseDto(product)).collect(Collectors.toList());
        if(!(searchList.size() == 0)){
            return searchList;
        }
        else {
            throw new ProductException(ProductExceptionType.SEARCH_NOT_EXIST);
        }
    }
}