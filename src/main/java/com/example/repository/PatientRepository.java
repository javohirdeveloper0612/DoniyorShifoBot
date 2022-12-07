package com.example.repository;

import com.example.entity.PatientEntity;

import com.example.enums.Status;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PatientRepository extends CrudRepository<PatientEntity, Long> {
    List<PatientEntity> getByFullNameIgnoreCaseAndStatus(String name,Status status);
    Integer countByFloor(String floor);
    List<PatientEntity> findAllByStatus(Status status);
    List<PatientEntity> getByFullNameIgnoreCase(String name);
}
