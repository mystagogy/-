package com.miniproject.backend.like.repository;

import com.miniproject.backend.like.domain.Like;
import com.miniproject.backend.loanproduct.domain.LoanProduct;
import com.miniproject.backend.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LikeRepository extends JpaRepository<Like, Long> {

    //boolean existsBy(User user, LoanProduct loanProduct);
}
