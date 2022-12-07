package com.example.owner.service;

import com.example.dto.PatientDTO;
import com.example.entity.PatientEntity;
import com.example.owner.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<PatientDTO> getPatientByFullName(String name) {

        List<PatientDTO> dtoList = new ArrayList<>();

        List<PatientEntity> entityList = patientRepository.getByFullNameIgnoreCase(name);

        if (entityList == null) {
            return null;
        }

        entityList.forEach(patientEntity -> dtoList.add(toDTO(patientEntity)));


        return dtoList;
    }

    public Integer getCountFloor(String floorNumber) {
        return patientRepository.countByFloorStartingWith(floorNumber);
    }

    public PatientDTO toDTO(PatientEntity entity) {
        PatientDTO dto = new PatientDTO();
        dto.setId(entity.getId());
        dto.setFullName(entity.getFullName());
        dto.setPhone(entity.getPhone());
        dto.setFloor(entity.getFloor());
        dto.setRoom(entity.getRoom());
        dto.setStatus(entity.getStatus());
        dto.setCreatedDate(entity.getCreatedDate());


        return dto;
    }

}
