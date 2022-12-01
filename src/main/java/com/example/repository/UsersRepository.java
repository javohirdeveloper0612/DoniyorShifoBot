package com.example.repository;

import com.example.entity.UsersEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UsersRepository extends CrudRepository<UsersEntity, Integer> {


    Optional<UsersEntity> findByPassword(String password);
}
