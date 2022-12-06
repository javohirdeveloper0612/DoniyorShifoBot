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

    private final MyTelegramBot myTelegramBot;

    private final AccountentService accountentService;

    private List<TelegramUsers> usersList = new ArrayList<>();

    public AdminController(AccountentService accountentService, MyTelegramBot myTelegramBot) {
        this.accountentService = accountentService;
        this.myTelegramBot = myTelegramBot;
    }

    public void handle(Message message) {

        TelegramUsers users = saveUser(message.getChatId());

        String text = message.getText();


        if (users.getStep() == null) {
            mainMenu(message);
            users.setStep(Step.MAIN);


        }

        if (users.getStep().equals(Step.MAIN)) {

            switch (text) {
                case Constant.addAccountent -> {
                    accountentService.getAccountentFullName(message);
                    users.setStep(Step.GETACCOUNTENTFULLNAME);
                    return;
                }

                case Constant.addNurse -> {

                }

                case Constant.deleteNurse -> {

                }

                case Constant.deletAccountent -> {

                }

                case Constant.listAccountent -> {

                }

                case Constant.listNurse -> {

                }


            }
        }

        switch (users.getStep()) {

            case GETACCOUNTENTFULLNAME: {
                accountentService.getAccountentPhone(message);
                users.setStep(Step.GETACCOUNTENTPHONE);
                return;
            }

            case GETACCOUNTENTPHONE: {
                boolean b = accountentService.checkPhone(message);
                accountentService.getAccountentPassword(message);
                users.setStep(Step.GETACCOUNTENTPASSWORD);
                return;
            }

            case GETACCOUNTENTPASSWORD: {
                accountentService.saveAccountent(message);
                mainMenu(message);
                users.setStep(Step.MAIN);
            }


        }
    }


    public void mainMenu(Message message) {
        myTelegramBot.send(SendMsg.sendMsgParse(message.getChatId(),
                "*Assalomu alekum admin panelga  menyuga xush kelibsiz*",
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
