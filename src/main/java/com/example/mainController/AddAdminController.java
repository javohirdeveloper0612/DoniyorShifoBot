package com.example.mainController;

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
public class AddAdminController {
    private List<TelegramUsers> usersList = new ArrayList<>();
    private final MainMenuController mainMenuController;

    private final MainController mainController;

    private final MyTelegramBot myTelegramBot;

    public AddAdminController(MainMenuController mainMenuController, MainController mainController, MyTelegramBot myTelegramBot) {
        this.mainMenuController = mainMenuController;
        this.mainController = mainController;
        this.myTelegramBot = myTelegramBot;
    }

    public void handle(Message message) {

        TelegramUsers users = mainController.saveUser(message.getChatId());

        TelegramUsers step = saveUser(message.getChatId());

        String text = message.getText();

        if (step.getStep() == null) {
            step.setStep(Step.START);
        }

        if (step.getStep().equals(Step.START)) {
            switch (text) {
                case Constant.addAdmin -> {
                    //dataBasedan 2 chi qavat dagi barcha bemorlar soni
                    enterPhone(message);
                    step.setStep(Step.PHONE);
                }

                case Constant.removeAdmin -> {
                    //dataBasedan 3 chi qavat dagi barcha bemorlar soni
                }

                case Constant.backToMenu -> {
                    mainMenuController.mainMenu(message);
                    users.setStep(Step.MAIN);
                }
            }
        }

        if (step.getStep().equals(Step.PHONE)) {
            //shu yerdan admin phone raqamini set qilib olamiz
            myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                    "Ushbu "));
        }

    }

    public void enterPhone(Message message) {
        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                "Qushmoqchi bolgan Admin telefon raqamini kiriting: "));
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
