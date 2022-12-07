package com.example.owner.mainController;

import com.example.owner.service.PatientService;
import com.example.step.Constant;
import com.example.step.Step;
import com.example.step.TelegramUsers;
import com.example.telegramBot.MyTelegramBot;
import com.example.util.SendMsg;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.ArrayList;
import java.util.List;

@Controller
public class PatientCountController {


    private List<TelegramUsers> usersList = new ArrayList<>();
    private final MainMenuController mainMenuController;

    private final MainController mainController;

    private final PatientService patientService;
    private final MyTelegramBot myTelegramBot;

    public PatientCountController(MainMenuController mainMenuController, MainController mainController, PatientService patientService, MyTelegramBot myTelegramBot) {
        this.mainMenuController = mainMenuController;
        this.mainController = mainController;
        this.patientService = patientService;
        this.myTelegramBot = myTelegramBot;
    }

    public void handle(Message message) {

        TelegramUsers users = mainController.saveUser(message.getChatId());

        TelegramUsers step = saveUser(message.getChatId());

        String text = message.getText();

        if (step.getStep() == null) {
            step.setStep(Step.COUNT);
        }

        if (step.getStep().equals(Step.COUNT)) {
            switch (text) {
                case Constant.qavat_2 -> {
                    //dataBasedan 2 chi qavat dagi barcha bemorlar soni

                    Integer count = patientService.getCountFloor("2");

                    myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                            "2⃣-qavatda Bemorlar soni: " + count));
                }

                case Constant.qavat_3 -> {
                    //dataBasedan 3 chi qavat dagi barcha bemorlar soni
                    Integer count = patientService.getCountFloor("3");

                    myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                            "3⃣-qavatda Bemorlar soni: " + count));
                }

                case Constant.backToMenu -> {
                    mainMenuController.mainMenu(message);
                    users.setStep(Step.MAIN);
                }
            }

        }


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
