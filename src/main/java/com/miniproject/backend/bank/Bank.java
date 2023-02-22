package com.miniproject.backend.bank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.persister.walking.spi.CollectionDefinition;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "bank")
@Getter

public class Bank {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "bank_id")
    private Long id;

    @Column(name = "bank_name")
    private String bankNm;

    @Column(name = "icon_path", columnDefinition = "TEXT")
    private String imgPath;
}
