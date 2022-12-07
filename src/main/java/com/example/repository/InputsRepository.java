package com.example.repository;

import com.example.entity.InputEntity;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface InputsRepository extends CrudRepository<InputEntity, Integer> {

    Optional<InputEntity> getByCreatedDate(LocalDate localDate);

    List<InputEntity> getByCreatedDateBetweenOrderByCreatedDateDesc(LocalDate before, LocalDate after);

}
