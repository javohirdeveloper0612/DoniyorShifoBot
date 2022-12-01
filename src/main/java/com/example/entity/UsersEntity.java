package com.example.entity;

import com.example.enums.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Entity
@Table(name = "users")
public class UsersEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String fullName;

    @Column(unique = true)
    private String phone;

    @Column(unique = true)
    private String password;

    private Long userId;

    @Enumerated(EnumType.STRING)
    private UserRole role;


}
