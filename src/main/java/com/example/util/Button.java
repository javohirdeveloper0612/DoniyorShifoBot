package com.example.util;

import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Button {
    public static KeyboardButton button(String text) {
        KeyboardButton keyboardButton = new KeyboardButton();
        keyboardButton.setText(text);
        return keyboardButton;
    }

    public static KeyboardButton button() {
        KeyboardButton keyboardButton = new KeyboardButton();
        keyboardButton.setRequestContact(true);
        return keyboardButton;
    }

    public static KeyboardRow row(KeyboardButton... keyboardButtons) {
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRow.addAll(Arrays.asList(keyboardButtons));
        return keyboardRow;
    }

    public static List<KeyboardRow> rowList(KeyboardRow... keyboardRows) {
        List<KeyboardRow> rows = new LinkedList<>();
        rows.addAll(Arrays.asList(keyboardRows));

        return rows;
    }

    public static ReplyKeyboardMarkup markup(List<KeyboardRow> rowList) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setKeyboard(rowList);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);
        return replyKeyboardMarkup;
    }


}
