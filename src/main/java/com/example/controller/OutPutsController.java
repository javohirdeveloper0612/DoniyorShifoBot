package com.example.controller;

import com.example.step.Constant;
import com.example.step.Step;
import com.example.step.TelegramUsers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.ArrayList;
import java.util.List;

@Controller
public class OutPutsController {

    private List<TelegramUsers> usersList = new ArrayList<>();

    @Autowired
    private MainController mainController;

    @Autowired
    private MainMenuController menuController;


    public void handle(Message message) {

        TelegramUsers users = mainController.saveUser(message.getChatId());

        TelegramUsers outPuts = saveUser(message.getChatId());

        String text = message.getText();

        if (outPuts.getStep() == null) {
            outPuts.setStep(Step.OUTPUTS);
        }

        if (outPuts.getStep().equals(Step.OUTPUTS)) {

            switch (text) {
                case Constant.naxd -> {
                    //naxd
                    menuController.naxdMenuChiqim(message);
                    outPuts.setStep(Step.NAXD);
                }

                case Constant.plastik -> {
                    //plastik
                    menuController.plastikChiqimMenu(message);
                    outPuts.setStep(Step.PLASTIK);
                }

                case Constant.umumiyBlance -> {
                    //umumiy
                    menuController.totalAmountOutputs(message);
                    outPuts.setStep(Step.TOTALAMOUNTOUTPUTS);

                }


                case Constant.backToMenu -> {
                    menuController.mainMenu(message);
                    users.setStep(Step.MAIN);
                }
            }

        }

        if (outPuts.getStep().equals(Step.PLASTIK)) {
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

                    menuController.outPutsMenu(message);
                    outPuts.setStep(Step.OUTPUTS);
                }
            }


        }
        if (outPuts.getStep().equals(Step.NAXD)) {
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

                    menuController.outPutsMenu(message);
                    outPuts.setStep(Step.OUTPUTS);
                }
            }
        }


        if (outPuts.getStep().equals(Step.TOTALAMOUNTOUTPUTS)) {
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

                    menuController.outPutsMenu(message);
                    outPuts.setStep(Step.OUTPUTS);
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
