package com.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter

public class ProfitDTO {

    private Double sum;

    private LocalDate created_date;

}
