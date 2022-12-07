package com.example.owner.service;

import com.example.dto.InputDTO;
import com.example.entity.InputEntity;
import com.example.owner.repository.InputsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InputsService {

    private final InputsRepository repository;

    public InputsService(InputsRepository repository) {
        this.repository = repository;
    }


    public InputDTO getInputCashByCreatedDate() {

        Optional<InputEntity> optional = repository.getByCreatedDate(LocalDate.now());

        if (optional.isEmpty()) {

            return null;
        }
        InputEntity inputEntity = optional.get();

        return toDTO(inputEntity);
    }

    public InputDTO getInputCardByCreatedDate() {

        Optional<InputEntity> optional = repository.getByCreatedDate(LocalDate.now());

        if (optional.isEmpty()) {

            return null;
        }
        InputEntity inputEntity = optional.get();

        return toDTO(inputEntity);
    }


    public InputDTO getInputByGivenDate(LocalDate localDate) {
        Optional<InputEntity> optional = repository.getByCreatedDate(localDate);

        if (optional.isEmpty()) {

            return null;
        }
        InputEntity inputEntity = optional.get();

        return toDTO(inputEntity);
    }

    public List<InputDTO> getInputLast10(LocalDate date){

        LocalDate before = date.minusDays(10);
        

       List<InputEntity> entities= repository.getByCreatedDateBetweenOrderByCreatedDateDesc(before,date);


       List<InputDTO> dtoList = new ArrayList<>();

      if (entities.isEmpty()){
          return null;
      }

       entities.forEach(entity -> dtoList.add(toDTO(entity)));


       return dtoList;

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
