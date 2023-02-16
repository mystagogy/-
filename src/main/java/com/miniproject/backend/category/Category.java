package com.miniproject.backend.category;

import lombok.Getter;
import javax.persistence.*;

@Entity
@Getter
public class Category {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ca_id")
    private Long id;

    @Column(name = "ca_name")
    private String categoryName;

    @Column(name = "ca_num")
    private String categoryNum;
}
