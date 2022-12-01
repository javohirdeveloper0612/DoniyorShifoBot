package com.example.service;

import com.example.entity.UsersEntity;
import com.example.repository.UsersRepository;
import com.example.telegramBot.MyTelegramBot;
import com.example.util.SendMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersService {


    @Autowired
    private MyTelegramBot myTelegramBot;

    @Autowired
    private UsersRepository usersRepository;


    public String getPassword(String password, Long userId) {

        Optional<UsersEntity> byPassword = usersRepository.findByPassword(password);
        if (byPassword.isEmpty()) {

            myTelegramBot.send(SendMsg.sendMsg(userId,
                    "Parol xato Iltimos qaytadan urinib ko'ring"));

        } else {
            UsersEntity entity = byPassword.get();

            return String.valueOf(entity.getRole());

        }

        UsersEntity entity = byPassword.get();

        return String.valueOf(entity.getRole());

    }

}
