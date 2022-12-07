package com.example.service;

import com.example.entity.UsersEntity;
import com.example.owner.repository.UsersRepository;
import com.example.telegramBot.MyTelegramBot;
import com.example.util.SendMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Optional;

@Service
public class UsersService {


    @Autowired
    private MyTelegramBot myTelegramBot;

    @Autowired
    private UsersRepository usersRepository;


    public String getUserId(Long userId) {
        Optional<UsersEntity> optional = usersRepository.findByUserId(userId);
        if (optional.isEmpty()) {
            return null;
        }
        UsersEntity usersEntity = optional.get();
        System.out.println(usersEntity.getRole());

        return String.valueOf(usersEntity.getRole());
    }

    public void checkPasssword(Message message) {
        myTelegramBot.send(SendMsg.sendMsg(message.getChatId()
                , "Iltimos bot dan foydalanish uchun sizga berilgan parolni kiriting : "));
    }


    public boolean getByPassword(Message message) {
        return usersRepository.existsByPassword(message.getText());
    }

    public void sendErrorPassword(Message message) {
        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                "Parol xato ! Iltimos qaytadan urinib ko'ring !"));
    }



}
