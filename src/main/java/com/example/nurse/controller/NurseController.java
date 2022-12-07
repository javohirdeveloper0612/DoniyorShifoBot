package com.example.nurse.controller;
import com.example.nurse.payload.NurseDTO;
import com.example.nurse.service.NurseService;
import com.example.step.Constant;
import com.example.step.Step;
import com.example.step.TelegramUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.objects.Message;
import java.util.ArrayList;
import java.util.List;

@Controller
public class NurseController {
    private List<TelegramUsers> usersList = new ArrayList<>();
    @Autowired
    private NurseService nurseService;
    NurseDTO dto = new NurseDTO();

    public void handleNurse(Message message) {

        TelegramUsers step = saveUser(message.getChatId());
        String text = message.getText();


        if (text.equals("/start") || step.getStep() == null) {
            nurseService.nurseMenuButton(message);
            step.setStep(Step.START);
        }

        if (step.getStep().equals(Step.START)) {

            switch (text) {

                case Constant.bemorQoshish -> {
                    nurseService.enterFullName(message);
                    step.setStep(Step.PATIENTFULLNAME);
                    return;
                }

                case Constant.bemorQidirish -> {
                    nurseService.searchPatientNameAndSurname(message);
                    step.setStep(Step.SEARCHPATIENT);
                    return;
                }

                case Constant.bemorOchirish -> {
                    nurseService.deletePatientById(message);
                    step.setStep(Step.DELETEDPATIENT);
                    return;
                }

                case Constant.bemorlarRoyhati -> {
                    nurseService.patientList(message);
                    step.setStep(Step.START);
                    return;
                }

            }

        }

        //***************************** PATIENT REGISTRATION **************************************


        switch (step.getStep()) {

            case PATIENTFULLNAME -> {
                dto.setFullName(text);
                nurseService.enterPhone(message);
                step.setStep(Step.PATIENTPHONE);
            }

            case PATIENTPHONE -> {
                boolean checkphone = nurseService.checkPhone(message);
                if (checkphone) {
                    dto.setPhone(text);
                    nurseService.enterFloor(message);
                    step.setStep(Step.PATIENTFLOOR);
                }
            }

            case PATIENTFLOOR -> {
                dto.setFloor(text);
                nurseService.enterHouse(message);
                step.setStep(Step.PATIENTHOUSE);
            }

            case PATIENTHOUSE -> {
                dto.setRoom(text);
                nurseService.endPatientRegistration(message);
                nurseService.nurseMenuButton2(message);
                step.setStep(Step.START);
                nurseService.creationPatient(dto);
            }

        }

        //************************** SEARCH PATIENT *******************************************

        if (step.getStep().equals(Step.SEARCHPATIENT)) {
            boolean searchpatient = nurseService.handlePatient(message);
            if(searchpatient){
                nurseService.nurseMenuButton2(message);
                step.setStep(Step.START);
            }
        }

        //************************* PATIENT DELETE ******************************************

        if (step.getStep().equals(Step.DELETEDPATIENT)) {
            boolean deleted = nurseService.deletedById(message);
            if (deleted) {
                nurseService.enddeletedPatient(message);
                nurseService.nurseMenuButton2(message);
                step.setStep(Step.START);
            }
        }

        //******************************************************************************
    }


    public TelegramUsers saveUser(Long chatId) {

        for (TelegramUsers users : usersList) {
            if (users.getChatId().equals(chatId)) {
                return users;
            }
        }
//        userController.getStep(chatId);


        TelegramUsers users = new TelegramUsers();
        users.setChatId(chatId);
        usersList.add(users);

        return users;
    }

}
