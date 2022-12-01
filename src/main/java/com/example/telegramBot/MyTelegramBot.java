package com.example.telegramBot;

import com.example.config.BotConfig;
import com.example.controller.AccountentController;
import com.example.controller.AdminController;
import com.example.controller.NurseController;
import com.example.mainController.MainController;
import com.example.service.UsersService;
import com.example.util.SendMsg;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendVideo;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class MyTelegramBot extends TelegramLongPollingBot {

    private final MainController mainController;
    private final AdminController adminController;
    private final NurseController nurseController;

    private final AccountentController accountentController;

    private final UsersService usersService;

    private final BotConfig botConfig;

    public MyTelegramBot(BotConfig botConfig, MainController mainController, AdminController adminController, NurseController nurseController, AccountentController accountentController, UsersService usersService) {
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

        Message message = update.getMessage();

        mainController.handle(update);


        if (userId == 191794566) {
            mainController.handle(update);
            return;
        }


        String role = usersService.getPassword(message.getText(), message.getChatId());

        if (role.equals("ADMIN")) {
            //admin controllerga junatiladi
            adminController.handle(message);
            return;
        }

        if (role.equals("NURSE")) {
            //nurse controller ga junatiladi
            nurseController.handle(message);
            return;
        }

        if (role.equals("ACCOUNTENT")) {
            accountentController.handle(message);
            return;
        }

        send(SendMsg.sendMsg(message.getChatId(),
                "Iltimos Botdan foydalanish uchun sizga berilgan parolni kiriting ! "));


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
