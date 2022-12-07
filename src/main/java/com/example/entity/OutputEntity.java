package com.example.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.ZoneId;

@Getter
@Setter

@Entity
@Table(name = "output")
public class OutputEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double card;

    private Double cash;

    @Column(name = "total")
    private Double totalAmount;

    @Column(name = "created_date")
    private LocalDate createdDate = LocalDate.now(ZoneId.of("Asia/Tashkent"));


}
