package com.example.entity;

import com.example.enums.Status;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.ZoneId;

@Getter
@Setter

@Entity
@Table(name = "patient")
public class PatientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    private String phone;

    private String floor;

    private String room;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "created_date")
    private LocalDate createdDate = LocalDate.now(ZoneId.of("Asia/Tashkent"));

}
