package com.example.dto;

import com.example.enums.UserRole;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Setter
@Getter


public class UsersDTO {

    private Integer id;

    private String fullName;

    private String phone;

    private String password;

    private Long userId;

    private UserRole role;
}
