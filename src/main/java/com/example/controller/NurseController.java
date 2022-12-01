package com.example.controller;

import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.objects.Message;

@Controller
public class NurseController {
    public void handle(Message message) {

        String text = message.getText();

    }
}
