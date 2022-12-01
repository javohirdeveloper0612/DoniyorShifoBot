package com.example.controller;

import com.example.step.Constant;
import com.example.step.Step;
import com.example.step.TelegramUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Controller
public class SickCountController {


    private List<TelegramUsers> usersList = new ArrayList<>();
    @Autowired
    private MainMenuController mainMenuController;

    @Autowired
    private MainController mainController;


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
                }

                case Constant.qavat_3 -> {
                    //dataBasedan 3 chi qavat dagi barcha bemorlar soni
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
