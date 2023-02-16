package com.miniproject.backend.loanproduct.service.impl;

import com.miniproject.backend.loanproduct.domain.LoanProduct;
import com.miniproject.backend.loanproduct.dto.ProductListDTO;
import com.miniproject.backend.loanproduct.exception.ProductException;
import com.miniproject.backend.loanproduct.exception.ProductExceptionType;
import com.miniproject.backend.loanproduct.repository.ProductRepository;
import com.miniproject.backend.loanproduct.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<ProductListDTO> selectProductList() {
        List<LoanProduct> productsList = productRepository.findAll();

        return productsList.stream()
                .map(ProductListDTO::new)
                .collect(Collectors.toList());
    }

    @Override
    public LoanProduct findProductByProductId(String productId) {
        Optional<LoanProduct> loanProduct = productRepository.findById(productId);
        if(!loanProduct.isEmpty()){
            return loanProduct.get();
        }else{
            throw new ProductException(ProductExceptionType.PRODUCT_NOT_EXIST);
        }
    }
}