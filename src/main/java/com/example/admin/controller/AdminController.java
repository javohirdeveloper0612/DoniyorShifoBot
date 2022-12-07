package com.example.admin.controller;

import com.example.admin.service.AccountentServiceAdmin;
import com.example.admin.service.NurseServiceAdmin;
import com.example.entity.UsersEntity;
import com.example.enums.UserRole;
import com.example.repository.UsersRepository;
import com.example.step.Constant;
import com.example.step.Step;
import com.example.step.TelegramUsers;
import com.example.telegramBot.MyTelegramBot;
import com.example.util.Button;
import com.example.util.SendMsg;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController {

    private final MyTelegramBot myTelegramBot;
    private final AccountentServiceAdmin accountentService;
    private final NurseServiceAdmin nurseService;

    private final UsersRepository userRepository;
    private UsersEntity nurseDTO = new UsersEntity();
    private UsersEntity accountentDTO = new UsersEntity();

    private List<TelegramUsers> usersList = new ArrayList<>();

    public AdminController(AccountentServiceAdmin accountentService, MyTelegramBot myTelegramBot, NurseServiceAdmin nurseService, UsersRepository userRepository) {
        this.accountentService = accountentService;
        this.myTelegramBot = myTelegramBot;
        this.nurseService = nurseService;
        this.userRepository = userRepository;
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
                    nurseService.getNurseFullName(message);
                    users.setStep(Step.GETNURSEFULLNAME);
                    return;
                }

                case Constant.deleteNurse -> {
                    nurseService.getNurseId(message);
                    users.setStep(Step.GETNURSEID);
                    return;

                }

                case Constant.deletAccountent -> {
                    accountentService.getAccountentId(message);
                    users.setStep(Step.GETACCOUNTENTID);
                    return;
                }

                case Constant.listAccountent -> {
                    accountentService.accountentList(message);
                    return;
                }

                case Constant.listNurse -> {

                }

                case Constant.backToMenu -> {
                    mainMenu(message);
                    users.setStep(Step.MAIN);
                    return;
                }


            }
        }

        switch (users.getStep()) {

            case GETACCOUNTENTFULLNAME: {
                accountentDTO.setFullName(message.getText());
                accountentService.getAccountentPhone(message);
                users.setStep(Step.GETACCOUNTENTPHONE);
                return;
            }

            case GETACCOUNTENTPHONE: {
                boolean checkPhone = accountentService.checkPhone(message);
                boolean existsByPhone = userRepository.existsByPhone(message.getText());
                if (checkPhone) {
                    if (existsByPhone) {
                        accountentService.exisByPhone(message);
                    } else {
                        accountentDTO.setPhone(message.getText());
                        accountentService.getAccountentPassword(message);
                        users.setStep(Step.GETACCOUNTENTPASSWORD);

                    }

                }

                return;
            }

            case GETACCOUNTENTPASSWORD: {
                boolean password = userRepository.existsByPassword(message.getText());
                if (password) {
                    accountentService.checkpassword(message);
                } else {
                    accountentDTO.setPassword(message.getText());
                    accountentDTO.setRole(UserRole.ACCOUNTENT);
                    userRepository.save(accountentDTO);
                    accountentDTO = new UsersEntity();
                    accountentService.saveAccountent(message);
                    users.setStep(Step.MAIN);
                }
                return;

            }

            case GETNURSEFULLNAME: {
                nurseDTO.setFullName(message.getText());
                nurseService.getNursePhone(message);
                users.setStep(Step.GETNURSEPHONE);
                return;
            }

            case GETNURSEPHONE: {
                boolean checkPhone = nurseService.checkPhone(message);
                boolean existsByPhone = userRepository.existsByPhone(message.getText());
                if (checkPhone) {
                    if (existsByPhone) {
                        nurseService.exisByPhone(message);
                    } else {
                        nurseDTO.setPhone(message.getText());
                        nurseService.getNursePassword(message);
                        users.setStep(Step.GETNURSEPASSWORD);
                    }

                }
                return;

            }

            case GETNURSEPASSWORD: {
                boolean exists = userRepository.existsByPassword(message.getText());
                if (exists) {
                    nurseService.checkpassword(message);
                } else {
                    nurseDTO.setPassword(message.getText());
                    nurseDTO.setRole(UserRole.NURSE);
                    userRepository.save(nurseDTO);
                    nurseDTO = new UsersEntity();
                    nurseService.saveNurse(message);
                    users.setStep(Step.MAIN);

                }

                return;
            }

            case GETNURSEID: {
                boolean nurseId = nurseService.checkNurseId(message);
                if (nurseId) {
                    users.setStep(Step.MAIN);
                }
                return;
            }

            case GETACCOUNTENTID: {
                boolean accountentId = accountentService.checkAccountentId(message);
                if (accountentId) {
                    users.setStep(Step.MAIN);
                }
                return;
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
