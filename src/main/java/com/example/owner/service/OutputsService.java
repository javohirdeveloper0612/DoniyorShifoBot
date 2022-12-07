package com.example.owner.service;

import com.example.dto.OutputsDTO;
import com.example.entity.OutputEntity;
import com.example.owner.repository.OutputsRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OutputsService {

    private final OutputsRepository repository;

    public OutputsService(OutputsRepository repository) {
        this.repository = repository;
    }

    public OutputsDTO getInputCashByCreatedDate() {

        Optional<OutputEntity> optional = repository.getByCreatedDate(LocalDate.now());

        if (optional.isEmpty()) {

            return null;
        }
        OutputEntity outputEntity = optional.get();

        return toDTO(outputEntity);
    }



    public OutputsDTO getInputByGivenDate(LocalDate localDate) {
        Optional<OutputEntity> optional = repository.getByCreatedDate(localDate);

        if (optional.isEmpty()) {

            return null;
        }
        OutputEntity inputEntity = optional.get();

        return toDTO(inputEntity);
    }

    public List<OutputsDTO> getInputLast10(LocalDate date){

        LocalDate before = date.minusDays(10);

        List<OutputEntity> entities= repository.getByCreatedDateBetweenOrderByCreatedDateDesc(before,date);


        List<OutputsDTO> dtoList = new ArrayList<>();

        if (entities.isEmpty()){
            return null;
        }

        entities.forEach(entity -> dtoList.add(toDTO(entity)));


        return dtoList;

    }


    public OutputsDTO toDTO(OutputEntity entity) {
        OutputsDTO dto = new OutputsDTO();
        dto.setId(entity.getId());
        dto.setCash(entity.getCash());
        dto.setCard(entity.getCard());
        dto.setTotalAmount(entity.getTotalAmount());
        dto.setCreatedDate(entity.getCreatedDate());

        return dto;
    }
}
