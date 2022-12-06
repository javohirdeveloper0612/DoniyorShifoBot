package com.example.repository;

import com.example.entity.PatientEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PatientRepository extends CrudRepository<PatientEntity, Long> {
    List<PatientEntity> getByFullNameIgnoreCase(String name);

    Integer countByFloor(String floor);
}
