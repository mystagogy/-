package com.miniproject.backend.loanproduct.service.impl;

import com.miniproject.backend.loanproduct.domain.LoanProduct;
import com.miniproject.backend.loanproduct.dto.PagingDTO;
import com.miniproject.backend.loanproduct.dto.ProductDetailDTO;
import com.miniproject.backend.loanproduct.dto.ProductListDTO;
import com.miniproject.backend.loanproduct.dto.SearchResponseDto;
import com.miniproject.backend.loanproduct.exception.ProductException;
import com.miniproject.backend.loanproduct.exception.ProductExceptionType;
import com.miniproject.backend.loanproduct.repository.ProductRepository;
import com.miniproject.backend.loanproduct.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Override
    public ProductDetailDTO findById(String productId) {
        LoanProduct loanProduct = productRepository.findById(productId).orElseThrow();
        return new ProductDetailDTO(loanProduct);
    }


    @Override
    public PagingDTO searchList(String keyword, Pageable pageable) {

        List<SearchResponseDto> searchList = new ArrayList<>();
        Page<LoanProduct> products = productRepository.findByProductNmContaining(keyword, pageable);
        int pages = products.getTotalPages();
        for(LoanProduct product : products){
            searchList.add(SearchResponseDto.builder()
                    .productId(product.getId())
                    .bankImgPath(product.getBank().getImgPath())
                    .bankName(product.getBank().getBankNm())
                    .productName(product.getProductNm())
                    .loanRateList(product.getLoanRates())
                    .loanLimit(product.getLoanLimit())
                    .build());
        }
        if(searchList.size() != 0){
            return PagingDTO.builder()
                    .num(searchList.size())
                    .searchList(searchList)
                    .current(pageable.getPageNumber())
                    .total(pages)
                    .build();
        }else{
            throw new ProductException(ProductExceptionType.SEARCH_NOT_EXIST);
        }
    }
}