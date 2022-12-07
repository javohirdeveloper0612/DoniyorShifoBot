package com.example.repository;

import com.example.entity.InputEntity;
import com.example.entity.OutputEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface OutputsRepository extends JpaRepository<OutputEntity,Integer> {

    Optional<OutputEntity> getByCreatedDate(LocalDate localDate);

    List<OutputEntity> getByCreatedDateBetweenOrderByCreatedDateDesc(LocalDate before, LocalDate after);
}
