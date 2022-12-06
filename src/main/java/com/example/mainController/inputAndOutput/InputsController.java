package com.example.mainController.inputAndOutput;

import com.example.dto.InputDTO;
import com.example.mainController.MainController;
import com.example.mainController.MainMenuController;
import com.example.service.InputsService;
import com.example.step.Constant;
import com.example.step.Step;
import com.example.step.TelegramUsers;
import com.example.telegramBot.MyTelegramBot;
import com.example.util.SendMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class InputsController {

    @Autowired
    private MainMenuController menuController;

    @Autowired
    private MyTelegramBot myTelegramBot;

    @Autowired
    private MainController mainController;

    @Autowired
    private InputsService inputsService;

    private List<TelegramUsers> usersList = new ArrayList<>();


    public void handle(Message message) {


        TelegramUsers users = mainController.saveUser(message.getChatId());

        TelegramUsers inPuts = saveUser(message.getChatId());


        String text = message.getText();

        if (inPuts.getStep() == null) {
            inPuts.setStep(Step.INPUTS);
        }

        if (inPuts.getStep().equals(Step.INPUTS)) {


            switch (text) {
                case Constant.naxd -> {
                    //naxd
                    menuController.naxdKirimMenu(message);
                    inPuts.setStep(Step.NAXDIN);
                }

                case Constant.plastik -> {
                    //plastik
                    menuController.plastikKirimMenu(message);
                    inPuts.setStep(Step.PLASTIKIN);
                }

                case Constant.umumiyBlance -> {
                    //umumiy
                    menuController.totalAmount(message);
                    inPuts.setStep(Step.TOTALAMOUNTINPUTS);
                }


                case Constant.backToMenu -> {
                    menuController.mainMenu(message);
                    users.setStep(Step.MAIN);
                }
            }

            return;

        }


        if (inPuts.getStep().equals(Step.NAXDIN)) {
            switch (text) {
                case Constant.bugungi -> {
                    // bugungilarni chiqramiz dataBaseda olinadi

                    InputDTO dto = inputsService.getInputCashByCreatedDate();

                    if (dto == null) {
                        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                                "Bugun hisobidan kirimlar kiritilmagan !"));
                        return;
                    }

                    myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                            "Bugun: " + dto.getCreatedDate() + "\n\n" +
                                    "Bugungi naxd kirimlar miqdori = " + dto.getCash() + " so'm\n"
                    ));
                    inPuts.setStep(Step.NAXDIN);

                }

                case Constant.kun10 -> {
                    //10 kunlikni hisoblab chiqarish
                }

                case Constant.kunBuyicha -> {
                    // kiritilgan sana buyicha kirimlar

                    myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                            "Qidirmoqchi bulgan sanani kiritig: masalan(2022-12-12)"));
                    inPuts.setStep(Step.PUTDATE);
                }

                case Constant.backToMenu -> {

                    menuController.inputsMenu(message);

                    inPuts.setStep(Step.INPUTS);
                }
            }
            return;
        }

        if (inPuts.getStep().equals(Step.PLASTIKIN)) {
            switch (text) {
                case Constant.bugungi -> {
                    // bugungilarni chiqramiz dataBaseda olinadi

                    InputDTO dto = inputsService.getInputCardByCreatedDate();

                    if (dto == null) {
                        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                                "Bugun hisobidan kirimlar kiritilmagan !"));
                        return;
                    }

                    myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                            "Bugun: " + dto.getCreatedDate() + "\n\n" +
                                    "Bugungi plastik kirimlar miqdori = " + dto.getCard() + " so'm\n"
                    ));

                }

                case Constant.kun10 -> {
                    //10 kunlikni hisoblab chiqarish
                }

                case Constant.kunBuyicha -> {
                    // kiritilgan sana buyicha kirimlar
                }

                case Constant.backToMenu -> {

                    menuController.inputsMenu(message);
                    inPuts.setStep(Step.INPUTS);
                }
            }
            return;
        }

        if (inPuts.getStep().equals(Step.TOTALAMOUNTINPUTS)) {
            switch (text) {
                case Constant.bugungi -> {
                    // bugungilarni chiqramiz dataBaseda olinadi

                    InputDTO dto = inputsService.getInputCashByCreatedDate();

                    if (dto == null) {
                        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                                "Bugun hisobidan kirimlar kiritilmagan !"));
                        return;
                    }

                    Double total = dto.getCard() + dto.getCash();
                    myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                            "Bugun: " + dto.getCreatedDate() + "\n\n" +
                                    "Bugungi umumiy kirimlar miqdori = " + total +
                                    " so'm\n"
                    ));

                }

                case Constant.kun10 -> {
                    //10 kunlikni hisoblab chiqarish
                }

                case Constant.kunBuyicha -> {
                    // kiritilgan sana buyicha kirimlar


                }

                case Constant.backToMenu -> {

                    menuController.inputsMenu(message);
                    inPuts.setStep(Step.INPUTS);
                }

            }
            return;
        }

        switch (inPuts.getStep()) {


            case PUTDATE -> {


                LocalDate date = null;


                try {

                    date = LocalDate.parse(message.getText());

                } catch (RuntimeException e) {
                    myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                            "Iltimos sanani to'g'ri kiriting !"));
                    return;
                }


                InputDTO dto = inputsService.getInputByGivenDate(date);

                if (dto == null) {
                    myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                            "Kiritilgan sana boyicha kirimlar topilmadi ! \n" +
                                    "Qaytadan kiriting ! "));
                    return;
                }


                myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                        "Ushbu " + dto.getCreatedDate() + "\n\n" +
                                "kundagi naxd kirimlar miqdori = " + dto.getCash() +
                                " so'm\n"));
                inPuts.setStep(Step.NAXDIN);

            }
        }

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
