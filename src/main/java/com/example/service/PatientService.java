package com.example.service;

import com.example.dto.PatientDTO;
import com.example.entity.PatientEntity;
import com.example.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

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
        return patientRepository.countByFloor(floorNumber);
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
