package com.example.controller;

import com.example.step.Constant;
import com.example.step.Step;
import com.example.step.TelegramUsers;
import com.example.telegramBot.MyTelegramBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.ArrayList;
import java.util.List;

@Controller
public class InputsController {

    @Autowired
    private MainMenuController menuController;

    @Autowired
    private MyTelegramBot myTelegramBot;

    @Autowired
    private MainController mainController;

    private List<TelegramUsers> usersList = new ArrayList<>();


    public void handle(Message message) {


        TelegramUsers users = mainController.saveUser(message.getChatId());

        TelegramUsers inPuts = saveUser(message.getChatId());


        String text = message.getText();

        if (inPuts.getStep() == null) {
            inPuts.setStep(Step.INPUTS);
        }

        if (inPuts.getStep().equals(Step.INPUTS)) {


            switch (text) {
                case Constant.naxd -> {
                    //naxd
                    menuController.naxdKirimMenu(message);
                    inPuts.setStep(Step.NAXDIN);
                }

                case Constant.plastik -> {
                    //plastik
                    menuController.plastikKirimMenu(message);
                    inPuts.setStep(Step.PLASTIKIN);
                }

                case Constant.umumiyBlance -> {
                    //umumiy
                    menuController.totalAmount(message);
                    inPuts.setStep(Step.TOTALAMOUNTINPUTS);
                }


                case Constant.backToMenu -> {
                    menuController.mainMenu(message);
                    users.setStep(Step.MAIN);
                }
            }

        }


        if (inPuts.getStep().equals(Step.NAXDIN)) {
            switch (text) {
                case Constant.bugungi -> {
                    // bugungilarni chiqramiz dataBaseda olinadi
                }

                case Constant.kun10 -> {
                    //10 kunlikni hisoblab chiqarish
                }

                case Constant.kunBuyicha -> {
                    // kiritilgan sana buyicha kirimlar
                }

                case Constant.backToMenu -> {

                    menuController.inputsMenu(message);
                    inPuts.setStep(Step.INPUTS);
                }
            }
        }

        if (inPuts.getStep().equals(Step.PLASTIKIN)) {
            switch (text) {
                case Constant.bugungi -> {
                    // bugungilarni chiqramiz dataBaseda olinadi
                }

                case Constant.kun10 -> {
                    //10 kunlikni hisoblab chiqarish
                }

                case Constant.kunBuyicha -> {
                    // kiritilgan sana buyicha kirimlar
                }

                case Constant.backToMenu -> {

                    menuController.inputsMenu(message);
                    inPuts.setStep(Step.INPUTS);
                }
            }
        }

        if (inPuts.getStep().equals(Step.TOTALAMOUNTINPUTS)) {
            switch (text) {
                case Constant.bugungi -> {
                    // bugungilarni chiqramiz dataBaseda olinadi
                }

                case Constant.kun10 -> {
                    //10 kunlikni hisoblab chiqarish
                }

                case Constant.kunBuyicha -> {
                    // kiritilgan sana buyicha kirimlar


                }

                case Constant.backToMenu -> {

                    menuController.inputsMenu(message);
                    inPuts.setStep(Step.INPUTS);
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
