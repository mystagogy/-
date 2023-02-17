package com.miniproject.backend.loanproduct.service;

import com.miniproject.backend.loanproduct.domain.LoanProduct;
import com.miniproject.backend.loanproduct.dto.ProductDetailDTO;
import com.miniproject.backend.loanproduct.dto.ProductListDTO;
import com.miniproject.backend.loanproduct.dto.SearchResponseDto;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface ProductService {

    List<ProductListDTO> selectProductList ();

    LoanProduct findProductByProductId(String productId);

    ProductDetailDTO findById (String ProductId);

    List<SearchResponseDto> searchList(String keyword);
}
