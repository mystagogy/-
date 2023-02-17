package com.miniproject.backend.shoppingbasket.repository;

import com.miniproject.backend.loanproduct.domain.LoanProduct;
import com.miniproject.backend.shoppingbasket.domain.Basket;
import com.miniproject.backend.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface BasketRepository extends JpaRepository<Basket,Long> {

    boolean existsByUserAndLoanProduct(User user,LoanProduct loanProduct);

    List<Basket> findByUser(User user);

    Optional<Basket> findByIdAndUser(Long basketId, User user);

    Optional<Basket> findByUserAndLoanProduct(User user, LoanProduct loanProduct);
}
