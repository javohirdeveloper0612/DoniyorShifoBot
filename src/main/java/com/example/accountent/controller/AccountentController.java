package com.example.accountent.controller;

import com.example.accountent.service.AccountentService;
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
public class AccountentController {
    @Autowired
    private MyTelegramBot myTelegramBot;
    List<TelegramUsers> usersList = new ArrayList<>();

    private final AccountentService accountentService;

    public AccountentController(AccountentService accountentService) {
        this.accountentService = accountentService;
    }

    public void handle(Message message) {
        String text = message.getText();

        TelegramUsers users = saveUser(message.getChatId());

        if (text.equals("/start") || users.getStep() == null) {
            mainMenu(message);
            users.setStep(Step.MAIN);
        }

        if (users.getStep().equals(Step.MAIN)) {

            switch (text) {
                case Constant.addInput -> {
                    //shu yerda kirimlar menusi
                    accountentService.addInputMenu(message);
                    users.setStep(Step.INPUTS);
                    return;
                }

                case Constant.addOutput -> {
                    //chiqimlar menusi
                    accountentService.addOutputMenu(message);
                    users.setStep(Step.OUTPUTS);
                    return;
                }

            }

            return;
        }
        if (users.getStep().equals(Step.INPUTS)) {

            // databasedan kirimlarni saqlaymiz

            switch (text) {
                case Constant.naxd -> {
                    accountentService.addKirimNaxd(message);
                    users.setStep(Step.NAXDIN);
                    return;
                }
                case Constant.plastik -> {
                    accountentService.addKidrimPlastik(message);
                    users.setStep(Step.PLASTIKIN);
                    return;
                }

                case Constant.backToMenu -> {
                    mainMenu(message);
                    users.setStep(Step.MAIN);
                    return;

                }


            }


        }

        if (users.getStep().equals(Step.NAXDIN)) {
            boolean saveCash = accountentService.saveCash(message);
            if (saveCash) {
                users.setStep(Step.INPUTS);
                return;
            }

        }

        if (users.getStep().equals(Step.PLASTIKIN)) {
            boolean saveCard = accountentService.saveCard(message);
            if (saveCard) {
                users.setStep(Step.INPUTS);
            }
        }


        if (users.getStep().equals(Step.OUTPUTS)) {

            switch (text) {
                case Constant.naxd -> {
                    accountentService.addChiqimNaxd(message);
                    users.setStep(Step.NAQDOUTPUTIN);
                    return;
                }
                case Constant.plastik -> {
                    accountentService.addChiqimPlastik(message);
                    users.setStep(Step.PLASTIKOUTPUTIN);
                    return;
                }
                case Constant.backToMenu -> {
                    mainMenu(message);
                    users.setStep(Step.MAIN);

                }

            }
        }

        if (users.getStep().equals(Step.NAQDOUTPUTIN)) {
            boolean output = accountentService.saveCashOutput(message);
            if (output) {
                users.setStep(Step.OUTPUTS);
                return;
            }
        }


        if (users.getStep().equals(Step.PLASTIKOUTPUTIN)) {
            boolean output = accountentService.saveCardOutput(message);
            if (output) {
                users.setStep(Step.OUTPUTS);

            }
        }


    }


    public void mainMenu(Message message) {
        myTelegramBot.send(SendMsg.sendMsgParse(message.getChatId(),
                "*Kirim-Chiqimlar paneliga xush kelibsiz*",
                Button.markup(Button.rowList(Button.row(
                        Button.button(Constant.addInput),
                        Button.button(Constant.addOutput)
                )))));

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
