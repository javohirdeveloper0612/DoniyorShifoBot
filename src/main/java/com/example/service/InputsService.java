package com.example.service;

import com.example.dto.InputDTO;
import com.example.entity.InputEntity;
import com.example.entity.UsersEntity;
import com.example.repository.InputsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class InputsService {

    @Autowired
    private InputsRepository inputsRepository;


    public InputDTO getInputCashByCreatedDate() {

        Optional<InputEntity> optional = inputsRepository.getByCreatedDate(LocalDate.now());

        if (optional.isEmpty()) {

            return null;
        }
        InputEntity inputEntity = optional.get();

        return toDTO(inputEntity);
    }

    public InputDTO getInputCardByCreatedDate() {

        Optional<InputEntity> optional = inputsRepository.getByCreatedDate(LocalDate.now());

        if (optional.isEmpty()) {

            return null;
        }
        InputEntity inputEntity = optional.get();

        return toDTO(inputEntity);
    }


    public InputDTO getInputByGivenDate(LocalDate localDate) {
        Optional<InputEntity> optional = inputsRepository.getByCreatedDate(localDate);

        if (optional.isEmpty()) {

            return null;
        }
        InputEntity inputEntity = optional.get();

        return toDTO(inputEntity);
    }


    public InputDTO toDTO(InputEntity entity) {
        InputDTO dto = new InputDTO();
        dto.setId(entity.getId());
        dto.setCash(entity.getCash());
        dto.setCard(entity.getCard());
        dto.setTotalAmount(entity.getTotalAmount());
        dto.setCreatedDate(entity.getCreatedDate());

        return dto;
    }
}
