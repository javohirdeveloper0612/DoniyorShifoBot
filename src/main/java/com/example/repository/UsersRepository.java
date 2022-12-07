package com.example.repository;

import com.example.entity.UsersEntity;
import com.example.enums.UserRole;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends CrudRepository<UsersEntity, Long> {


    boolean existsByPassword(String password);

    List<UsersEntity> findAllByRole(UserRole role);

    boolean existsByPhone(String phone);

    Optional<UsersEntity> findByUserId(Long userId);


}
