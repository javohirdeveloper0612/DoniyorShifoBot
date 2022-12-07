package com.example.owner.mainController;

import com.example.dto.OutputsDTO;
import com.example.dto.ProfitDTO;
import com.example.owner.ProfitMapper;
import com.example.owner.service.ProfitService;
import com.example.step.Constant;
import com.example.step.Step;
import com.example.step.TelegramUsers;
import com.example.telegramBot.MyTelegramBot;
import com.example.util.Button;
import com.example.util.SendMsg;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProfitController {


    private final MainMenuController menuController;


    private final MainController mainController;

    private final ProfitService service;


    private final MyTelegramBot myTelegramBot;


    private List<TelegramUsers> usersList = new ArrayList<>();

    @Lazy
    public ProfitController(MainMenuController menuController, MainController mainController, ProfitService service, MyTelegramBot myTelegramBot) {
        this.menuController = menuController;
        this.mainController = mainController;
        this.service = service;
        this.myTelegramBot = myTelegramBot;
    }


    public void handle(Message message) {


        TelegramUsers users = mainController.saveUser(message.getChatId());

        TelegramUsers residual = saveUser(message.getChatId());


        String text = message.getText();

        if (residual.getStep() == null) {
            residual.setStep(Step.RESIDUAL);
        }

        if (residual.getStep().equals(Step.RESIDUAL)) {


            switch (text) {
                case Constant.naxd -> {
                    //naxd
                    menuController.naxqQoldilar(message);
                    residual.setStep(Step.NAXD);
                }

                case Constant.plastik -> {
                    //plastik
                    menuController.plastikQoldilar(message);
                    residual.setStep(Step.PLASTIK);
                }

                case Constant.umumiyBlance -> {
                    //umumiy
                    menuController.totalResidualOutputs(message);
                    residual.setStep(Step.TOTALAMOUNTINPUTS);
                }


                case Constant.backToMenu -> {
                    menuController.mainMenu(message);
                    users.setStep(Step.MAIN);
                }
            }

        }


        if (residual.getStep().equals(Step.NAXD)) {
            switch (text) {
                case Constant.bugungi -> {
                    // bugungilarni chiqramiz dataBaseda olinadi
                    ProfitMapper mapper = service.getByCreated_date(LocalDate.now());

                    if (mapper == null) {
                        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                                "Bugun hisobidan hisobotlar kiritilmagan ! ❌"));
                        return;
                    }


                    myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                            "Sana: " + mapper.getCreatedDate() + "\n\n" +
                                    "\uD83D\uDCB5 Bugungi naxq qoldiqlar miqdori = " + mapper.getCash() +
                                    " so'm\n"
                    ));

                }

                case Constant.kun10 -> {
                    //10 kunlikni hisoblab chiqarish

                    List<ProfitMapper> mappers = service.getLast10Profit(LocalDate.now());


                    if (mappers == null) {
                        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                                "Hozirgacha 10 kunlik hisobotlar mavjud emas ! ⚠"));
                        return;
                    }
                    for (ProfitMapper mapper : mappers) {
                        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                                "Sana: " + mapper.getCreatedDate() + "\n" +
                                        "\uD83D\uDCB5 Umimiy Summa: " + mapper.getCash() + " so'm \n"));
                    }
                }

                case Constant.kunBuyicha -> {
                    // kiritilgan sana buyicha kirimlar

                    myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                            "\uD83D\uDCC5 Qidirmoqchi bulgan sanani kiriting: masalan (2022-12-12)",
                            Button.markup(Button.rowList(Button.row(
                                    Button.button(Constant.backToMenu)
                            )))));
                    residual.setStep(Step.SEARCHDAYNAXD);

                }

                case Constant.backToMenu -> {

                    menuController.inputsMenu(message);
                    residual.setStep(Step.RESIDUAL);
                }
            }
            return;
        }

        if (residual.getStep().equals(Step.PLASTIK)) {
            switch (text) {
                case Constant.bugungi -> {
                    // bugungilarni chiqramiz dataBaseda olinadi
                    ProfitMapper mapper = service.getByCreated_date(LocalDate.now());

                    if (mapper == null) {
                        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                                "Bugun hisobidan hisobotlar kiritilmagan ! ❌"));
                        return;
                    }


                    myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                            "Sana: " + mapper.getCreatedDate() + "\n\n" +
                                    "\uD83D\uDCB5 Bugungi plastik qoldiqlar miqdori = " + mapper.getCard() +
                                    " so'm\n"
                    ));
                }

                case Constant.kun10 -> {
                    //10 kunlikni hisoblab chiqarish

                    List<ProfitMapper> mappers = service.getLast10Profit(LocalDate.now());


                    if (mappers == null) {
                        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                                "Hozirgacha 10 kunlik hisobotlar mavjud emas ! ⚠"));
                        return;
                    }
                    for (ProfitMapper mapper : mappers) {
                        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                                "Sana: " + mapper.getCreatedDate() + "\n" +
                                        "\uD83D\uDCB5 Umimiy Summa: " + mapper.getCard() + " so'm \n"));
                    }
                }

                case Constant.kunBuyicha -> {
                    // kiritilgan sana buyicha kirimlar
                    myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                            "\uD83D\uDCC5 Qidirmoqchi bulgan sanani kiriting: masalan (2022-12-12)",
                            Button.markup(Button.rowList(Button.row(
                                    Button.button(Constant.backToMenu)
                            )))));
                    residual.setStep(Step.SEARCHDAYPLASTIK);
                }

                case Constant.backToMenu -> {

                    menuController.inputsMenu(message);
                    residual.setStep(Step.RESIDUAL);
                }
            }
            return;
        }

        if (residual.getStep().equals(Step.TOTALAMOUNTINPUTS)) {
            switch (text) {
                case Constant.bugungi -> {
                    // bugungilarni chiqramiz dataBaseda olinadi

                    ProfitMapper mapper = service.getByCreated_date(LocalDate.now());

                    if (mapper == null) {
                        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                                "Bugun hisobidan hisobotlar kiritilmagan ! ❌"));
                        return;
                    }


                    myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                            "Sana: " + mapper.getCreatedDate() + "\n\n" +
                                    "\uD83D\uDCB5 Bugungi umumiy qoldiqlar miqdori = " + mapper.getTotal() +
                                    " so'm\n"
                    ));
                }

                case Constant.kun10 -> {
                    //10 kunlikni hisoblab chiqarish

                    List<ProfitMapper> mappers = service.getLast10Profit(LocalDate.now());


                    if (mappers == null) {
                        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                                "Hozirgacha 10 kunlik hisobotlar mavjud emas ! ⚠"));
                        return;
                    }
                    for (ProfitMapper mapper : mappers) {
                        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                                "Sana: " + mapper.getCreatedDate() + "\n" +
                                        "\uD83D\uDCB5 Umimiy Summa: " + mapper.getTotal() + " so'm \n"));
                    }
                }

                case Constant.kunBuyicha -> {
                    // kiritilgan sana buyicha kirimlar

                    myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                            "\uD83D\uDCC5 Qidirmoqchi bulgan sanani kiriting: masalan (2022-12-12)",
                            Button.markup(Button.rowList(Button.row(
                                    Button.button(Constant.backToMenu)
                            )))));
                    residual.setStep(Step.SEARCHDAYTOTAL);
                }

                case Constant.backToMenu -> {

                    menuController.inputsMenu(message);
                    residual.setStep(Step.RESIDUAL);
                }
            }
            return;
        }
        switch (residual.getStep()) {

            case SEARCHDAYNAXD -> {

                if (message.getText().equals(Constant.backToMenu)) {
                    menuController.naxqQoldilar(message);
                    residual.setStep(Step.NAXD);
                    return;
                }

                if (sendSummaByDateNaxd(message)) {
                    residual.setStep(Step.NAXD);
                }

            }

            case SEARCHDAYPLASTIK -> {
                if (message.getText().equals(Constant.backToMenu)) {
                    menuController.plastikKirimMenu(message);
                    residual.setStep(Step.NAXD);
                    return;
                }

                if (sendSummaByDatePlastik(message)) {

                    residual.setStep(Step.PLASTIK);
                }
            }

            case SEARCHDAYTOTAL -> {
                if (message.getText().equals(Constant.backToMenu)) {
                    menuController.totalAmount(message);
                    residual.setStep(Step.NAXD);
                    return;
                }
                if (sendSummaByDateTotal(message)) {

                    residual.setStep(Step.TOTALAMOUNTINPUTS);
                }

            }
        }

    }

    public boolean sendSummaByDateNaxd(Message message) {

        LocalDate date = null;


        try {

            date = LocalDate.parse(message.getText());

        } catch (RuntimeException e) {
            myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                    "Iltimos sanani to'g'ri kiriting ! ❌"));
            return false;
        }


        ProfitMapper mapper = service.getByCreated_date(date);

        if (mapper == null) {
            myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                    "Kiritilgan sana boyicha qoldiqlar topilmadi ! \n ❌" +
                            "🔁 Qaytadan kiriting ! "));
            return false;
        }


        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                "Sana " + mapper.getCreatedDate() + "\n\n" +
                        "\uD83D\uDCB5 Naxd qoldiqlar miqdori = " + mapper.getCash() +
                        " so'm\n"));
        return true;
    }

    public boolean sendSummaByDatePlastik(Message message) {

        LocalDate date = null;


        try {

            date = LocalDate.parse(message.getText());

        } catch (RuntimeException e) {
            myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                    "Iltimos sanani to'g'ri kiriting !"));
            return false;
        }


        ProfitMapper mapper = service.getByCreated_date(date);

        if (mapper == null) {
            myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                    "Kiritilgan sana boyicha qoldiqlar topilmadi ! \n ❌" +
                            "🔁 Qaytadan kiriting ! "));
            return false;
        }


        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                "Sana " + mapper.getCreatedDate() + "\n\n" +
                        "\uD83D\uDCB3 Plastik qoldiqlar miqdori = " + mapper.getCard() +
                        " so'm\n"));
        return true;
    }

    public boolean sendSummaByDateTotal(Message message) {

        LocalDate date = null;


        try {

            date = LocalDate.parse(message.getText());

        } catch (RuntimeException e) {
            myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                    "Iltimos sanani to'g'ri kiriting !"));
            return false;
        }


        ProfitMapper mapper = service.getByCreated_date(date);

        if (mapper == null) {
            myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                    "Kiritilgan sana boyicha qoldiqlar topilmadi ! \n ❌" +
                            "🔁 Qaytadan kiriting ! "));
            return false;
        }


        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                "Sana " + mapper.getCreatedDate() + "\n\n" +
                        "\uD83D\uDCB5 kundagi umimiy qoldiqlar miqdori = " + mapper.getTotal() +
                        " so'm\n"));
        return true;
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
