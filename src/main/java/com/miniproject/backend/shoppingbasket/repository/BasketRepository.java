package com.miniproject.backend.shoppingbasket.repository;

import com.miniproject.backend.shoppingbasket.domain.Basket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasketRepository extends JpaRepository<Basket,Long> {
}
