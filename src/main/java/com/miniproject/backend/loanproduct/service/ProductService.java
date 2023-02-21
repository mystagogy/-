package com.miniproject.backend.loanproduct.service;

import com.miniproject.backend.loanproduct.domain.LoanProduct;
import com.miniproject.backend.loanproduct.dto.PagingDTO;
import com.miniproject.backend.loanproduct.dto.ProductDto;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public interface ProductService {

    List<ProductDto.Response> selectProductList();

    LoanProduct findProductByProductId(String productId);

    ProductDto.DetailResponse findById(String ProductId);

    ProductDto.PagingDTO searchList(String keyword, Pageable pageable);
}
