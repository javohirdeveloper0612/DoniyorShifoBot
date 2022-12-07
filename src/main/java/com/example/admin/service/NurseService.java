package com.example.admin.service;


import com.example.entity.UsersEntity;
import com.example.enums.Status;
import com.example.repository.UsersRepository;
import com.example.step.Constant;
import com.example.telegramBot.MyTelegramBot;
import com.example.util.Button;
import com.example.util.SendMsg;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Optional;

@Service
public class NurseService {

    private final MyTelegramBot myTelegramBot;

    private final UsersRepository usersRepository;


    public NurseService(MyTelegramBot myTelegramBot, UsersRepository usersRepository) {
        this.myTelegramBot = myTelegramBot;

        this.usersRepository = usersRepository;

    }


    public void getNurseFullName(Message message) {
        myTelegramBot.send(SendMsg.sendMsgParse(message.getChatId(),
                "Iltimos Hamshiraning ismi va familiyasini quyidagi shakilda kiriting " +
                        "\n*Masalan : Abdullayeva Maftuna*"));
    }


    public void getNursePhone(Message message) {
        myTelegramBot.send(SendMsg.sendMsgParse(message.getChatId(),
                "Iltimos Hamshiraning telefon raqamini quyidagi shakilda kiriting" +
                        "\n*Masalan : +998971234567*"));
    }

    public void getNursePassword(Message message) {
        myTelegramBot.send(SendMsg.sendMsgParse(message.getChatId(),
                "Iltimos hamshira ushbu bot dan foydalanish uchun parol o'rnating " +
                        "\n*Eslatma : Parol takrorlanmas bo'lish kerak !*"));
    }


    public void saveNurse(Message message) {
        myTelegramBot.send(SendMsg.sendMsgParse(message.getChatId(),
                "*Muvaffaqqiyatli saqlandi ✅✅ *",
                Button.markup(Button.rowList(Button.row(Button.button(Constant.backToMenu))))));
    }

    public boolean checkPhone(Message message) {
        if (!message.getText().startsWith("+998") || message.getText().length() != 13) {
            myTelegramBot.send(SendMsg.sendMsgParse(message.getChatId(),
                    "Iltimos telefon raqamni quyidagi ko'rinishda kiriting !" +
                            "*\nMasalan : +998971234567*"));
            return false;
        }
        return true;
    }

    public void checkpassword(Message message) {
        myTelegramBot.send(SendMsg.sendMsgParse(message.getChatId(),
                "*Bunday parol bazada mavjud. Iltimos boshqa parol kiriting !*"));
    }

    public void exisByPhone(Message message) {
        myTelegramBot.send(SendMsg.sendMsgParse(message.getChatId(),
                "*Ushbu telefon nomer ba'zada mavjud !*"));
    }


    public void getNurseId(Message message) {
        myTelegramBot.send(SendMsg.sendMsgParse(message.getChatId(),
                "Iltimos Hamshiraning ID raqamini kiriting " +
                        "\n*Eslatma : ID raqamni  Hamshiralar ro'yxatidan olishingiz mumkin !*"));
    }


    public boolean checkNurseId(Message message) {

        String text = message.getText();
        for (int i = 0; i < text.length(); i++) {
            if (!Character.isDigit(text.charAt(i)) || Character.isLetter(text.charAt(i))) {
                myTelegramBot.send(SendMsg.sendMsgParse(message.getChatId(),
                        "*ID raqamda harf yoki belgi bo'lishi mumkin emas !*"));
                return false;
            }
        }

        Long id = Long.valueOf(message.getText());
        Optional<UsersEntity> optionalUsers = usersRepository.findById(id);
        if (optionalUsers.isEmpty()) {
            myTelegramBot.send(SendMsg.sendMsgParse(message.getChatId(),
                    "*Ushbu ID li Hamshira mavjud emas !*"));
            return false;
        } else {
            UsersEntity usersEntity = optionalUsers.get();
            if (!usersEntity.getStatus().equals(Status.BLOCK)) {
                usersEntity.setStatus(Status.BLOCK);
                usersRepository.save(usersEntity);
                myTelegramBot.send(SendMsg.sendMsgParse(message.getChatId(),
                        "*Muvaffaqqiyatli o'chirildi ✅✅*",
                        Button.markup(Button.rowList(Button.row(Button.button(Constant.backToMenu))))));
                return true;
            } else {
                myTelegramBot.send(SendMsg.sendMsgParse(message.getChatId(),
                        "*Ushbu ID li Hamshira mavjud emas !*"));
                return false;
            }
        }

    }
}
