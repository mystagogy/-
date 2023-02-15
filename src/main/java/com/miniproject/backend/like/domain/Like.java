package com.miniproject.backend.like.domain;

import com.miniproject.backend.loanproduct.domain.LoanProduct;
import com.miniproject.backend.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "likes")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Like {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "likes_id")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id ")
    private LoanProduct loanProduct;
}
