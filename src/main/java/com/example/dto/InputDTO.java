package com.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter

public class InputDTO {
    private Integer id;

    private Double card;

    private Double cash;


    private Double totalAmount;


    private LocalDate createdDate;

}
