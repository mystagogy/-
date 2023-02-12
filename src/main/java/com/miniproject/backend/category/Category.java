package com.miniproject.backend.category;

import javax.persistence.*;

@Entity
public class Category {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ca_id")
    private Long id;

    @Column(name = "ca_name")
    private String categoryName;

    @Column(name = "ca_num")
    private String categoryNum;
}
