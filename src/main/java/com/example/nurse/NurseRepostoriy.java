package com.example.nurse;
import com.example.entity.PatientEntity;
import org.springframework.data.repository.CrudRepository;

public interface NurseRepostoriy extends CrudRepository<PatientEntity,Integer> {


}
