package com.miniproject.backend.shoppingbasket.domain;

import com.miniproject.backend.loanproduct.domain.LoanProduct;
import com.miniproject.backend.user.domain.User;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "shopping_basket")
public class Basket {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "shopping_basket_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id ")
    private LoanProduct loanProduct;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
