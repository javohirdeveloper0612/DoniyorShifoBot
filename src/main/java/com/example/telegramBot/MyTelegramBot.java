package com.example.telegramBot;

import com.example.config.BotConfig;
import com.example.accountent.AccountentController;
import com.example.admin.AdminController;
import com.example.mainController.MainController;
import com.example.nurse.controller.NurseController;
import com.example.service.UsersService;
import com.example.step.Step;
import com.example.step.TelegramUsers;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Component
public class MyTelegramBot extends TelegramLongPollingBot {

    List<TelegramUsers> usersList = new ArrayList<>();
    private final MainController mainController;
    private final AdminController adminController;
    private final NurseController nurseController;

    private final AccountentController accountentController;

    private final UsersService usersService;

    private final BotConfig botConfig;

    public MyTelegramBot(@Lazy BotConfig botConfig, @Lazy MainController mainController, @Lazy AdminController adminController, @Lazy NurseController nurseController, @Lazy AccountentController accountentController, @Lazy UsersService usersService) {
        this.botConfig = botConfig;
        this.mainController = mainController;
        this.adminController = adminController;
        this.nurseController = nurseController;
        this.accountentController = accountentController;
        this.usersService = usersService;
    }


    @Override
    public void onUpdateReceived(Update update) {
        Long userId = update.getMessage().getFrom().getId();
        TelegramUsers telegramUsers = saveUser(userId);

        Message message = update.getMessage();

        if (userId == 1024661500) {
            mainController.handle(message);
            return;
        }
        if (userId == 1541606718) {
            nurseController.handleNurse(message);
            return;
        }

        if (userId == 1030035146) {
            adminController.handle(message);
            return;
        }

        boolean check = false;
        boolean password;
        if (message.getText().equals("/start")) {
            usersService.checkPasssword(message);
            telegramUsers.setStep(Step.CHECKPASSWORD);
            check = true;
            return;
        }

        if (telegramUsers.getStep().equals(Step.CHECKPASSWORD)) {
            if (check) {
                password = usersService.getByPassword(message);
            } else {
                password = true;
            }
            if (password) {
                String role = usersService.getUserId(userId);
                if (role != null) {
                    if (role.equals("NURSE")) {
                        nurseController.handleNurse(message);

                        return;
                    }
                    if (role.equals("ACCOUNTENT")) {
                        accountentController.handle(message);
                        return;
                    }
                    if (role.equals("ADMIN")) {
                        adminController.handle(message);
                    }
                }
            } else {
                usersService.sendErrorPassword(message);
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

    public void send(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void send(SendPhoto sendPhoto) {
        try {
            execute(sendPhoto);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void send(SendVideo sendVideo) {
        try {
            execute(sendVideo);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void send(SendDocument sendDocument) {
        try {
            execute(sendDocument);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }
}
