package com.example.accountent;

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

    public void handle(Message message) {
        String text = message.getText();

        TelegramUsers users = saveUser(message.getChatId());

        if (text.equals("/start") || users.getStep() == null){
            mainMenu(message);

            users.setStep(Step.MAIN);
        }
        if (users.getStep().equals(Step.MAIN)){

        switch (text) {
            case Constant.addInput -> {
                //shu yerda kirimlar menusi
                addInputMenu(message);
                users.setStep(Step.INPUTS);
                return;
            }

            case Constant.addOutput -> {
                //chiqimlar menusi
                addOutputMenu(message);
                users.setStep(Step.OUTPUTS);
            }

        }

        return;
        }
        if ( users.getStep().equals(Step.INPUTS)){

            // databasedan kirimlarni saqlaymiz

            switch (text){
                case Constant.naxd -> {
                    addKirimNaxd(message);
                    users.setStep(Step.NAXDIN);
                }
                case Constant.plastik -> {
                    addKidrimPlastik(message);
                    users.setStep(Step.PLASTIKIN);
                }

            }
            return;
        }
        if (users.getStep().equals(Step.OUTPUTS)){

            switch (text){
                case Constant.naxd -> {
                    addChiqimNaxd(message);
                    users.setStep(Step.NAXDIN);
                }
                case Constant.plastik -> {
                    addChiqimPlastik(message);
                    users.setStep(Step.PLASTIKIN);
                }

            }
            return;

        }
    }

    private void addChiqimPlastik(Message message) {
        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                "Kirimlarning naxd qismini kiriting :"));
    }

    private void addChiqimNaxd(Message message) {
        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                "Chiqimlarning naxd qismini kiriting :"));
    }

    private void addKidrimPlastik(Message message) {
        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                "Chiqimlarning naxd qismini kiriting :"));
    }

    private void addKirimNaxd(Message message) {
        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                "Kirimlarning naxd qismini kiriting :"));
    }

    public void addInputMenu(Message message){
        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                "Kiririmlarni kiritish ",
                Button.markup(Button.rowList(Button.row(
                        Button.button(Constant.naxd),
                        Button.button(Constant.plastik)) ,
                        Button.row(
                                Button.button(Constant.backToMenu)
                        )))));

    }

    public void addOutputMenu(Message message){
        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                "Chiqimlarni kiritish  ",
                Button.markup(Button.rowList(Button.row(
                                Button.button(Constant.naxd),
                                Button.button(Constant.plastik)) ,
                        Button.row(
                                Button.button(Constant.backToMenu)
                        )))));

    }

    public void mainMenu(Message message) {
        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                "Kirim-Chiqimlar paneliga xush kelibsiz",
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
