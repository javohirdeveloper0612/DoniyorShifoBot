package com.example.admin;

import com.example.telegramBot.MyTelegramBot;
import com.example.util.SendMsg;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
public class AccountentService {

    private final MyTelegramBot myTelegramBot;

    public AccountentService(MyTelegramBot myTelegramBot) {
        this.myTelegramBot = myTelegramBot;
    }

    public void getAccountentFullName(Message message) {
        myTelegramBot.send(SendMsg.sendMsgParse(message.getChatId(),
                "Iltimos Kassirning ismi va familiyasini quyidagi ko'rinishida kiriting\n" +
                        "*Masalan : Ali Aliyev*"));
    }

    public void getAccountentPhone(Message message) {
        myTelegramBot.send(SendMsg.sendMsgParse(message.getChatId(),
                "Iltimos Kassirning telefon raqamini quyidagi ko'rinishda kiriting\n" +
                        "*Masalan : +998971234567*"));
    }


    public void getAccountentPassword(Message message) {
        myTelegramBot.send(SendMsg.sendMsgParse(message.getChatId(),
                "Iltimos kassir ushbu botdan foydalanish uchun parol o'rnating !" +
                        "\n*Eslatma : parol takrorlanmas bo'lish kerak*"));
    }

    public void saveAccountent(Message message) {
        myTelegramBot.send(SendMsg.sendMsgParse(message.getChatId(),
                "*Muvafaqqiyatli saqlandi ✅✅ *"));
    }

    public boolean checkPhone(Message message) {
        if (!message.getText().startsWith("+998") && message.getText().length() != 13) {
            myTelegramBot.send(SendMsg.sendMsgParse(message.getChatId(),
                    "Iltimos telefon raqamni quyidagi ko'rinishda kiriting !" +
                            "*\nMasalan : +998971234567*"));
            return false;
        }
        return true;
    }
}
