package com.example.owner.service;

import com.example.dto.InputDTO;
import com.example.dto.OutputsDTO;
import com.example.dto.ProfitDTO;
import com.example.owner.ProfitMapper;
import com.example.owner.repository.ProfitRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ProfitService {


    private final OutputsService outputsService;

    private final InputsService inputsService;

    private final ProfitRepository profitRepository;

    @Lazy
    public ProfitService(OutputsService outputsService, InputsService inputsService, ProfitRepository profitRepository) {

        this.outputsService = outputsService;
        this.inputsService = inputsService;
        this.profitRepository = profitRepository;
    }

//    public ProfitDTO getTodayProfit(){
//
//        InputDTO inputDTO =inputsService.getInputCashByCreatedDate();
//
//        OutputsDTO outputsDTO = outputsService.getInputByGivenDate(LocalDate.now());
//
//        if (inputDTO==null && outputsDTO !=null){
//            ProfitDTO dto =new ProfitDTO();
//
//            Double sum = outputsDTO.getCash();
//            dto.setSum(sum);
//            dto.setCreated_date(outputsDTO.getCreatedDate());
//
//            return dto;
//        }
//
//        if (inputDTO!=null && outputsDTO ==null){
//            ProfitDTO dto =new ProfitDTO();
//
//            Double sum = inputDTO.getCash();
//            dto.setSum(sum);
//            dto.setCreated_date(inputDTO.getCreatedDate());
//
//            return dto;
//        }
//
//        if (inputDTO==null && outputsDTO ==null){
//           return null;
//        }
//
//        ProfitDTO dto =new ProfitDTO();
//
//        Double sum =inputDTO.getCash() - outputsDTO.getCash();
//        dto.setSum(sum);
//        dto.setCreated_date(outputsDTO.getCreatedDate());
//
//        return dto;
//
//
//    }


    public ProfitMapper getByCreated_date(LocalDate localDate) {
        ProfitMapper profitMapper = profitRepository.getByCreatedDate(localDate);
        if (profitMapper == null) {
            return null;
        }

        return profitMapper;
    }

    public List<ProfitMapper> getLast10Profit(LocalDate localDate) {

        List<ProfitMapper> mappers = profitRepository.getByCreatedDateBetween(localDate.minusDays(10), localDate);

        if (mappers.isEmpty()) {
            return null;
        }

        return mappers;

    }

    /*public ProfitDTO getTodayProfitPlastik(){
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

    }*/
}
