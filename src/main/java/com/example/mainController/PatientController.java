package com.example.mainController;

import com.example.dto.PatientDTO;
import com.example.service.PatientService;
import com.example.telegramBot.MyTelegramBot;
import com.example.util.SendMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;

@Controller
public class PatientController {
    @Autowired
    private PatientService patientService;

    @Autowired
    private MyTelegramBot myTelegramBot;


    public void handle(Message message) {

        List<PatientDTO> dtoList = patientService.getPatientByFullName(message.getText());

        if (dtoList != null) {

            for (PatientDTO dto : dtoList) {
                myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                        "Ismi: " + dto.getFullName() + "\n\n" +
                                "\uD83D\uDED7 Qavati: " + dto.getFloor() + "\n\n" +
                                "\uD83C\uDFD8 Xona raqami: " + dto.getRoom() + "\n\n" +
                                "☎ Telefon raqami: " + dto.getPhone() + "\n\n" +
                                "\uD83D\uDCC5 Kelgan kuni: " + dto.getCreatedDate()));
            }

        }
    }
}
