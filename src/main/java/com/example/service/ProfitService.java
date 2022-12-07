package com.example.service;

import com.example.dto.ProfitDTO;
import com.example.entity.InputEntity;
import com.example.entity.OutputEntity;
import com.example.repository.InputsRepository;
import com.example.repository.OutputsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class ProfitService {
    private final InputsRepository inputsRepository;

    private final OutputsRepository outputsRepository;

    public ProfitService(InputsRepository inputsRepository, OutputsRepository outputsRepository) {
        this.inputsRepository = inputsRepository;
        this.outputsRepository = outputsRepository;
    }

    public ProfitDTO getTodayProfit(){
        Optional<OutputEntity>  optionalOutput = outputsRepository.getByCreatedDate(LocalDate.now());
        Optional<InputEntity>  optionalInput = inputsRepository.getByCreatedDate(LocalDate.now());


        if (optionalOutput.isEmpty() && optionalInput.isEmpty()){
            return null;
        }

        OutputEntity outputEntity = optionalOutput.get();
        InputEntity inputEntity =optionalInput.get();
        ProfitDTO dto =new ProfitDTO();

        Double sum = inputEntity.getCash()-outputEntity.getCash();
        dto.setSum(sum);
        dto.setCreated_date(LocalDate.now());

        return dto;

    }

    public ProfitDTO getTodayProfitPlastik(){
        Optional<OutputEntity>  optionalOutput = outputsRepository.getByCreatedDate(LocalDate.now());
        Optional<InputEntity>  optionalInput = inputsRepository.getByCreatedDate(LocalDate.now());


        if (optionalOutput.isEmpty() && optionalInput.isEmpty()){
            return null;
        }

        OutputEntity outputEntity = optionalOutput.get();
        InputEntity inputEntity =optionalInput.get();
        ProfitDTO dto =new ProfitDTO();

        Double sum = inputEntity.getCash()-outputEntity.getCash();
        dto.setSum(sum);
        dto.setCreated_date(LocalDate.now());

        return dto;

    }
}
