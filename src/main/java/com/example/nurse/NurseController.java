package com.example.nurse;
import com.example.step.Constant;
import com.example.step.Step;
import com.example.step.TelegramUsers;
import com.example.telegramBot.MyTelegramBot;
import com.example.util.Button;
import com.example.util.SendMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.objects.Message;
import java.util.ArrayList;
import java.util.List;

@Controller
public class NurseController {

    private List<TelegramUsers> usersList = new ArrayList<>();
    @Autowired
    private MyTelegramBot myTelegramBot;


    public void handleNurse(Message message) {

        TelegramUsers users = saveUser(message.getChatId());
        String text = message.getText();

        if (text.equals("/start")) {
            nurseMenuButton(message);
        }

        if (users.getStep() == null) {
            users.setStep(Step.PATIENTATART);
        }


        if (users.getStep().equals(Step.PATIENTATART)) {

            switch (text) {

                case Constant.bemorQoshish -> {

                    enterFullName(message);
                    users.setStep(Step.PATIENTPHONE);
                    return;

                }
                case Constant.bemorQidirish -> {


                }
                case Constant.bemorOchirish -> {


                }
                case Constant.bemorlarRoyhati -> {


                }
            }

            if (users.getStep().equals(Step.PATIENTPHONE)) {

                enterPhone(message);

            }
        }

    }


    public void enterFullName(Message message) {
        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(), "Bemorning  Ism va Familyasini kiriting : "));
    }

    public void enterPhone(Message message) {
        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(), "Bemorning telefon raqamini kiriting : (+99 89? ??? ?? ??)"));
    }

    public void enterFloor(Message message) {
        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(), "Bemorning honasini qavat raqamini kiriting : "));
    }

    public void enterHouse(Message message) {
        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(), "Bemorning hona raqamini kiriting :"));
    }

    public void searchPatientNameAndSurname(Message message) {
        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(), "Bemorni topish uchun bemorning Ism va Familyasini kiriting : "));
    }

    public void deletePatientNameAndSurname(Message message) {
        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(), "Bemorni o'chirish uchun bemorning Ism va Familyasini kiriting : "));
    }

    public void patientRoyxati(Message message) {
        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(), "Doniyor Shifo Klinikasidagi bemorlar ro'yxati : "));
    }


    public void nurseMenuButton(Message message) {

        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(), "Asalom Alaykum Hamshira Bo'limiga xush kelibsiz : ", Button.markup(Button.rowList(Button.row(Button.button(Constant.bemorQoshish), Button.button(Constant.bemorQidirish)), Button.row(Button.button(Constant.bemorOchirish), Button.button(Constant.bemorlarRoyhati))))));
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
