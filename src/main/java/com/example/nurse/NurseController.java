package com.example.nurse;

import com.example.step.Constant;
import com.example.step.Step;
import com.example.step.TelegramUsers;
import com.example.telegramBot.MyTelegramBot;
import com.example.util.Button;
import com.example.util.SendMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.ArrayList;
import java.util.List;

@Controller
public class NurseController {

    private List<TelegramUsers> usersList = new ArrayList<>();
    @Autowired
    private MyTelegramBot myTelegramBot;


    public void handle(Message message) {

        TelegramUsers users = saveUser(message.getChatId());
        String text = message.getText();

        if (users.getStep() == null) {
            nurseMenuButton(message);
            users.setStep(Step.ADDPATIENT);
        } else {
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(message.getChatId());
            sendMessage.setText("Kechirasiz Notog'ri buyruq kiritildi !!!");
        }

        if (users.getStep().equals(Step.ADDPATIENT)) {

            switch (text) {

                case Constant.bemorQoshish -> {
                    users.setStep(Step.PATIENTADD);


                    if (users.getStep().equals(Step.PATIENTADD)) {
                        users.setStep(Step.ENTERNAMEPATIENT);

                        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                                "Bemor qoshish"));

                        // BEMORNI ISMI KIRITILADI VA QABUL QILIB OLAMIZ

                        if (users.getStep().equals(Step.ENTERNAMEPATIENT)) {
                            users.setStep(Step.ENTERSURNAMEPATIENT);

                            // BEMORNI FAMILYASI KIRITILADI VA QABUL QILIB OLAMIZ

                        } else if (users.getStep().equals(Step.ENTERSURNAMEPATIENT)) {
                            users.setStep(Step.ENTERPHONEPATIENT);

                            // BEMORNI TELEFON RAQAM KIRITADI VA QABUL QILIB OLAMIZ

                        } else if (users.getStep().equals(Step.ENTERPHONEPATIENT)) {
                            users.setStep(Step.ENTERFLOORPATIENT);

                            //BEMORNI YOTGAN QAVAT RAQAMINI KIRITADI VA BIZ QABUL QILIB OLAMIZ


                        } else if (users.getStep().equals(Step.ENTERFLOORPATIENT)) {
                            users.setStep(Step.ENTERHOUSEPATIENT);

                            //BEMORNI YOTGAN HONA RAQAMINI KIRITADI

                        } else if (users.getStep().equals(Step.ENTERHOUSEPATIENT)) {
                            users.setStep(Step.FINISHEDADDPATIENT);

                            // BEMORNI YOTGAN HONA RAQAMINI QABUL QILIB OLAMIZ

                        } else if (users.getStep().equals(Step.FINISHEDADDPATIENT)) {

                            // Xamshiraga registratsiya tugagini aytamiz
                        }
                    }


                }
                case Constant.bemorQidirish -> {
                    users.setStep(Step.SEARCHPATIENT);

                    if (users.getStep().equals(Step.SEARCHPATIENT)) {
                        users.setStep(Step.SEARCHNAMEPATIENT);

                        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                                "Bemor Qidirish"));

                    } else if (users.getStep().equals(Step.SEARCHNAMEPATIENT)) {
                        users.setStep(Step.SEARCHSURNAMEPATIENT);


                    } else if (users.getStep().equals(Step.SEARCHSURNAMEPATIENT)) {

                        //  myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                        //                      "mazgi" ));

                    }


                }
                case Constant.bemorOchirish -> {
                    users.setStep(Step.DELETEDPATIENT);

                    if (users.getStep().equals(Step.DELETEDPATIENT)) {
                        users.setStep(Step.DELETEDBYIDPATIENT);

                        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                                "Bemor ochirish"));

                    } else if (users.getStep().equals(Step.DELETEDBYIDPATIENT)) {


                    }


                }
                case Constant.bemorlarRoyhati -> {
                    users.setStep(Step.PATIENTLIST);

                    if (users.getStep().equals(Step.PATIENTLIST)) {

                        // Bemorlar royhati excel formatda korsatiladi__

                    }

                    // Data Basedagi hama bemorlar royhatini
                    // Excel foirmatda tekshirish

                }
            }
        }


    }

    public void nurseMenuButton(Message message) {

        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                "Asalom Alaykum Hamshira Bo'limiga xush kelibsiz : ",
                Button.markup(Button.rowList(
                        Button.row(
                                Button.button(Constant.bemorQoshish),
                                Button.button(Constant.bemorQidirish)
                        ),
                        Button.row(
                                Button.button(Constant.bemorOchirish),
                                Button.button(Constant.bemorlarRoyhati)
                        )
                ))));
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
