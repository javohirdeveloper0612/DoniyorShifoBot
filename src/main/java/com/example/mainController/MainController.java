package com.example.mainController;

import com.example.mainController.inputAndOutput.InputsController;
import com.example.mainController.inputAndOutput.OutPutsController;
import com.example.step.Constant;
import com.example.step.Step;
import com.example.step.TelegramUsers;
import com.example.telegramBot.MyTelegramBot;
import com.example.util.SendMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {

    List<TelegramUsers> usersList = new ArrayList<>();
    @Autowired
    private MyTelegramBot myTelegramBot;

    @Autowired
    private MainMenuController menuController;

    @Autowired
    private InputsController inputsController;

    @Autowired
    private OutPutsController outPutsController;
    @Autowired
    private ProfitController profitController;

    @Autowired
    private PatientCountController patientCountController;

    @Autowired
    private AddAdminController addAdminController;
    @Autowired
    private PatientController patientController;


    public void handle(Message message) {

        TelegramUsers users = saveUser(message.getChatId());


        if (message.hasText()) {

            String text = message.getText();


            if (text.equals("/start") || users.getStep() == null) {
                menuController.mainMenu(message);
                users.setStep(Step.MAIN);
            }

            if (users.getStep().equals(Step.MAIN)) {

                switch (text) {


                    case Constant.kirim -> {

                        //krimlar
                        menuController.inputsMenu(message);
                        users.setStep(Step.INPUTS);

                        return;
                    }

                    case Constant.chiqim -> {
                        //chiqimlar
                        menuController.outPutsMenu(message);
                        users.setStep(Step.OUTPUTS);
                        return;
                    }

                    case Constant.qoldiq -> {
                        //qoldiqlar

                        menuController.qoldiqMenu(message);
                        users.setStep(Step.RESIDUAL);
                        return;
                    }

                    case Constant.bemorQidirish -> {

                        menuController.searchPatient(message);
                        users.setStep(Step.SEARCHPATIENT);
                    }

                    case Constant.bemorlarSoni -> {
                        //bemorlar soni
                        menuController.countSick(message);
                        users.setStep(Step.COUNT);
                        return;
                    }

                    case Constant.adminMenu -> {
                        //adminMenu
                        menuController.addAdminMenu(message);
                        users.setStep(Step.ADMIN);

                        return;
                    }
                }
                return;
            }


            //kirimlar ************************
            if (users.getStep().equals(Step.INPUTS)) {

                inputsController.handle(message);
                return;

            }


            //   chiqimlar ***********************************************


            if (users.getStep().equals(Step.OUTPUTS)) {
                outPutsController.handle(message);
                return;
            }

            if (users.getStep().equals(Step.RESIDUAL)) {
                profitController.handle(message);
                return;
            }

            if (users.getStep().equals(Step.COUNT)) {
                patientCountController.handle(message);
                return;
            }

            if (users.getStep().equals(Step.ADMIN)) {
                addAdminController.handle(message);
                return;
            }

            if (users.getStep().equals(Step.SEARCHPATIENT)) {
                patientController.handle(message);
                users.setStep(Step.MAIN);
                return;
            }


        } else {
            myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                    "not action"));
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
