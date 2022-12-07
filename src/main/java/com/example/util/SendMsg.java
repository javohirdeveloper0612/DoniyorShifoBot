package com.example.util;

import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

public class SendMsg {
    public static SendMessage sendMsg(Long id, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(id);
        sendMessage.setText(text);
        return sendMessage;
    }

    public static SendMessage sendMsg(Long id, String text, ReplyKeyboardMarkup markup) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(id);
        sendMessage.setText(text);
        sendMessage.setReplyMarkup(markup);

        return sendMessage;
    }

    public static SendPhoto sendPhoto(Long id, String text, String inputFile) {
        SendPhoto sendPhoto = new SendPhoto();
        InputFile input = new InputFile();
        input.setMedia(inputFile);
        sendPhoto.setChatId(id);
        sendPhoto.setPhoto(input);
        sendPhoto.setCaption(text);

        return sendPhoto;
    }

    public static SendMessage sendMsgParse(Long id, String text, ReplyKeyboardMarkup markup) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(id);
        sendMessage.setText(text);
        sendMessage.setParseMode("Markdown");
        sendMessage.setReplyMarkup(markup);

        return sendMessage;


    }

    public static SendMessage sendMsgParse(Long id, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(id);
        sendMessage.setText(text);
        sendMessage.setParseMode("Markdown");
        return sendMessage;
    }

    public static SendDocument sendDoc(Long id, InputFile inputFile, ReplyKeyboardMarkup markup) {

        SendDocument sendDocument = new SendDocument();
        sendDocument.setChatId(id);
        sendDocument.setDocument(inputFile);
        sendDocument.setReplyMarkup(markup);
        return sendDocument;
    }
}
