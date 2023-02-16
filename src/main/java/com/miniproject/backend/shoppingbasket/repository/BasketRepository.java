package com.miniproject.backend.shoppingbasket.repository;

import com.miniproject.backend.loanproduct.domain.LoanProduct;
import com.miniproject.backend.shoppingbasket.domain.Basket;
import com.miniproject.backend.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BasketRepository extends JpaRepository<Basket,Long> {

    boolean existsByUserAndLoanProduct(User user,LoanProduct loanProduct);


    int deleteByUserAndLoanProduct(User user,LoanProduct loanProduct);


}
