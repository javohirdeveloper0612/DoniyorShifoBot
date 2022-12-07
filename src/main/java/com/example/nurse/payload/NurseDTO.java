package com.example.nurse.payload;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter

public class NurseDTO {

    private Long id;
    private String fullName;
    private String phone;
    private String floor;
    private String room;
    private LocalDate created_date;



}
