package com.example.dto;

import com.example.enums.Status;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString


public class PatientDTO {
    private Long id;

    private String fullName;

    private String phone;

    private String floor;

    private String room;

    private Status status;

    private LocalDate createdDate;
}
