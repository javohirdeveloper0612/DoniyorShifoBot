package com.example.repository;

import com.example.entity.InputEntity;
import com.example.entity.OutputEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface OutputRepository extends JpaRepository<OutputEntity, Long> {
    Optional<OutputEntity> getByCreatedDate(LocalDate localDate);


    Optional<OutputEntity> findByCreatedDate(LocalDate now);
}
