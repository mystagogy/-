package com.miniproject.backend.loanproduct.repository;

import com.miniproject.backend.loanproduct.domain.LoanProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<LoanProduct, Long> {

    Optional<LoanProduct> findById(String productId);

    Page<LoanProduct> findByProductNmContaining(String keyword, Pageable pageable);


}
