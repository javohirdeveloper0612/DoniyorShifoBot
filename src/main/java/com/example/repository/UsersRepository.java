package com.example.repository;

import com.example.entity.UsersEntity;
import com.example.enums.UserRole;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UsersRepository extends CrudRepository<UsersEntity, Integer> {

    Optional<UsersEntity> findByUserId(Long userId);

    boolean existsByPassword(String password);

    List<UsersEntity> findAllByRole(UserRole role);

    boolean existsByPhone(String phone);
}
