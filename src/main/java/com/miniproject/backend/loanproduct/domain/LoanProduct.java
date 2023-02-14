package com.miniproject.backend.loanproduct.domain;

import com.miniproject.backend.bank.Bank;
import com.miniproject.backend.category.Category;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table()
public class LoanProduct {

    @Id
    @Column(name = "product_id")
    private String id;

    @Column(name = "loan_type")
    private String type;

    @OneToOne
    @JoinColumn(name = "ca_id")
    private Category category;

    @OneToOne
    @JoinColumn(name = "bank_id")
    private Bank bank; //제공 은행

    @Column(name = "product_name")
    private String productNm;

    @Column(name = "join_way")
    private String joinWay; //가입 방법

    @Column(name = "loan_inci_expn")
    private String loanIncidentalExpenses; //대출부대비용

    @Column(name = "erly_rpay_fee")
    private String earlyRepayFee; //중도상환수수료

    @Column(name = "dly_rate")
    private String delayRate; //연체이자율

    @Column(name = "loan_lmt")
    private String loanLimit; //대출한도

    @Column(name = "dcls_strt_day")
    private String disclosureStartDay; //공시시작일

    @Column(name = "dcls_end_day")
    private String disclosureEndDay; // 공시종료일

    @OneToMany(mappedBy = "product")
    private List<LoanRate> loanRates = new ArrayList<>();

}
