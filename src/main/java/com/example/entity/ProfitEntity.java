package com.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.ZoneId;

@Getter
@Setter

@Entity
@Table(name = "profit")
public class ProfitEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Double card;

    private Double cash;

    @Column(name = "total")
    private Double totalAmount;

    @Column(name = "created_date")
    private LocalDate createdDate = LocalDate.now(ZoneId.of("Asia/Tashkent"));


}