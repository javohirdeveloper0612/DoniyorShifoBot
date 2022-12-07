package com.example.owner.mainController;

import com.example.admin.controller.AdminController;
import com.example.nurse.service.NurseService;
import com.example.owner.mainController.inputAndOutput.InputsController;
import com.example.owner.mainController.inputAndOutput.OutPutsController;
import com.example.step.Constant;
import com.example.step.Step;
import com.example.step.TelegramUsers;
import com.example.telegramBot.MyTelegramBot;
import com.example.util.SendMsg;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {

    List<TelegramUsers> usersList = new ArrayList<>();
    private final MyTelegramBot myTelegramBot;

    private final MainMenuController menuController;

    private final InputsController inputsController;

    private final OutPutsController outPutsController;
    private final ProfitController profitController;

    private final PatientCountController patientCountController;

    private final PatientController patientController;
    private final AdminController adminController;

    private final NurseService nurseService;

    @Lazy
    public MainController(MyTelegramBot myTelegramBot, MainMenuController menuController,
                          InputsController inputsController, OutPutsController outPutsController,
                          ProfitController profitController, PatientCountController patientCountController,
                          PatientController patientController, AdminController adminController, NurseService nurseService) {

        this.myTelegramBot = myTelegramBot;
        this.menuController = menuController;
        this.inputsController = inputsController;
        this.outPutsController = outPutsController;
        this.profitController = profitController;
        this.patientCountController = patientCountController;

        this.patientController = patientController;
        this.adminController = adminController;
        this.nurseService = nurseService;
    }


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

                    case Constant.bemorlarRoyhati -> {
                        nurseService.patientList(message);
                        return;
                    }

                    case Constant.adminMenu -> {
                        //adminMenu
//                        menuController.addAdminMenu(message);
                        adminController.mainMenu(message);
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
                adminController.handle(message);
                return;
            }

            if (users.getStep().equals(Step.SEARCHPATIENT)) {
                patientController.handle(message);
                users.setStep(Step.MAIN);

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


        TelegramUsers users = new TelegramUsers();
        users.setChatId(chatId);
        usersList.add(users);

        return users;
    }
}
