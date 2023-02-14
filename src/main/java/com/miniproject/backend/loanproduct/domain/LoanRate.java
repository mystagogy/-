package com.miniproject.backend.loanproduct.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class LoanRate {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "loan_rate_id")
    private Long id;

    @Column(name = "lend_rate_type_nm")
    private String rateType; //대출금리유형

    @Column(name = "rpay_type_nm")
    private String repayType; //대출상환유형

    @Column(name = "lend_rate_min")
    private float minRate; //최저 금리

    @Column(name = "lend_rate_max")
    private float maxRate; //최고 금리

    @Column(name = "lend_rate")
    private float avgRate; //금리 평균

    @Column(name = "mrtg_type_nm")
    private String mortgageType; //주담대만 나머진 null


}
