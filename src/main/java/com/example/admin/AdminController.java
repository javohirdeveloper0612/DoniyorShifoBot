package com.example.admin;

import com.example.step.Constant;
import com.example.step.Step;
import com.example.step.TelegramUsers;
import com.example.telegramBot.MyTelegramBot;
import com.example.util.Button;
import com.example.util.SendMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private MyTelegramBot myTelegramBot;


    private List<TelegramUsers> usersList = new ArrayList<>();

    public void handle(Message message) {

        TelegramUsers users = saveUser(message.getChatId());

        String text = message.getText();


        if (text.equals("/start") || users.getStep() == null) {
            mainMenu(message);
            users.setStep(Step.MAIN);
        }

        if (users.getStep().equals(Step.MAIN)) {

            switch (text) {
                case Constant.addAccountent -> {
                    // yangi kasr qushish


                }
            }
        }
    }


    public void mainMenu(Message message) {
        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                "Assosiy menyuga xush kelibsiz",
                Button.markup(Button.rowList(Button.row(Button.button(Constant.addAccountent),
                                Button.button(Constant.addNurse)
                        ),
                        Button.row(Button.button(Constant.deletAccountent),
                                Button.button(Constant.deleteNurse)),
                        Button.row(Button.button(Constant.listAccountent),
                                Button.button(Constant.listNurse))
                ))
        ));
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
