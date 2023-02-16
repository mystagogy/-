package com.miniproject.backend.loanproduct.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "loan_rate")
@Getter
public class LoanRate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "loan_rate_id")
    private Long id;

    @Column(name = "lend_rate_type_nm")
    private String rateType; //대출금리유형

    @Column(name = "rpay_type_nm")
    private String repayType; //대출상환유형

    @Column(name = "lend_rate_min")
    private Float minRate; //최저 금리

    @Column(name = "lend_rate_max")
    private Float maxRate; //최고 금리

    @Column(name = "lend_rate")
    private Float avgRate; //금리 평균

    @Column(name = "mrtg_type_nm")
    private String mortgageType; //주담대만 나머진 null



}
