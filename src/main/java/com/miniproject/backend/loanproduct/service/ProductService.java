package com.miniproject.backend.loanproduct.service;

import com.miniproject.backend.loanproduct.dto.ProductListDTO;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface ProductService {

    List<ProductListDTO> selectProductList ();
}
