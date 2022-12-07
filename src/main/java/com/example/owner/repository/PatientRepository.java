package com.example.owner.repository;

import com.example.entity.PatientEntity;
import com.example.enums.Status;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PatientRepository extends CrudRepository<PatientEntity, Long> {
    List<PatientEntity> getByFullNameIgnoreCase(String name);

    Integer countByFloorStartingWith(String floor);

    List<PatientEntity> findAllByStatus(Status status);
}
