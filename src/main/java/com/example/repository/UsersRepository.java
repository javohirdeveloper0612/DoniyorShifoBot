package com.example.repository;

import com.example.entity.UsersEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends CrudRepository<UsersEntity, Long> {


    boolean existsByPassword(String password);

    boolean existsByPhone(String phone);

    Optional<UsersEntity> findByUserId(Long userId);


}
