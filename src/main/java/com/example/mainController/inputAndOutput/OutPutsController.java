package com.example.mainController.inputAndOutput;

import com.example.dto.InputDTO;
import com.example.dto.OutputsDTO;
import com.example.mainController.MainController;
import com.example.mainController.MainMenuController;
import com.example.service.OutputsService;
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
public class OutPutsController {

    private List<TelegramUsers> usersList = new ArrayList<>();

    private final MainController mainController;

    private final MainMenuController menuController;
    private final MyTelegramBot myTelegramBot;

    private final OutputsService service;

    @Lazy
    public OutPutsController(MainController mainController, MainMenuController menuController, MyTelegramBot myTelegramBot, OutputsService service) {
        this.mainController = mainController;
        this.menuController = menuController;
        this.myTelegramBot = myTelegramBot;
        this.service = service;
    }


    public void handle(Message message) {

        TelegramUsers users = mainController.saveUser(message.getChatId());

        TelegramUsers outPuts = saveUser(message.getChatId());

        String text = message.getText();

        if (outPuts.getStep() == null) {
            outPuts.setStep(Step.OUTPUTS);
        }

        if (outPuts.getStep().equals(Step.OUTPUTS)) {

            switch (text) {
                case Constant.naxd -> {
                    //naxd
                    menuController.naxdMenuChiqim(message);
                    outPuts.setStep(Step.NAXD);
                }

                case Constant.plastik -> {
                    //plastik
                    menuController.plastikChiqimMenu(message);
                    outPuts.setStep(Step.PLASTIK);
                }

                case Constant.umumiyBlance -> {
                    //umumiy
                    menuController.totalAmountOutputs(message);
                    outPuts.setStep(Step.TOTALAMOUNTOUTPUTS);

                }


                case Constant.backToMenu -> {
                    menuController.mainMenu(message);
                    users.setStep(Step.MAIN);
                }
            }

        }

        if (outPuts.getStep().equals(Step.PLASTIK)) {
            switch (text) {
                case Constant.bugungi -> {
                    // bugungilarni chiqramiz dataBaseda olinadi
                    OutputsDTO dto = service.getInputCashByCreatedDate();

                    if (dto == null) {
                        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                                "Bugun hisobidan chiqimlar kiritilmagan !"));
                        return;
                    }

                    myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                            "Sana: " + dto.getCreatedDate() + "\n\n" +
                                    "\uD83D\uDCB3 Plastik chiqimlar miqdori = " + dto.getCash() + " so'm\n"
                    ));

                }

                case Constant.kun10 -> {
                    //10 kunlikni hisoblab chiqarish
                    List<OutputsDTO> dtoList = service.getInputLast10(LocalDate.now());

                    if (dtoList == null) {
                        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                                "Hozirgacha 10 kunlik hisobotlar mavjud emas ! ⚠"));
                        return;
                    }
                    for (OutputsDTO dto : dtoList) {
                        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                                "Sana: " + dto.getCreatedDate() + "\n" +
                                        "\uD83D\uDCB5 Plastik Summa: " + dto.getCard() + " so'm \n"));
                    }
                }

                case Constant.kunBuyicha -> {
                    // kiritilgan sana buyicha kirimlar

                    myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                            "\uD83D\uDCC5 Qidirmoqchi bulgan sanani kiritig: masalan (2022-12-12)",
                            Button.markup(Button.rowList(Button.row(
                                    Button.button(Constant.backToMenu)
                            )))));
                    outPuts.setStep(Step.SEARCHDAYPLASTIK);
                }

                case Constant.backToMenu -> {

                    menuController.outPutsMenu(message);
                    outPuts.setStep(Step.OUTPUTS);
                }
            }

            return;
        }
        if (outPuts.getStep().equals(Step.NAXD)) {
            switch (text) {
                case Constant.bugungi -> {
                    // bugungilarni chiqramiz dataBaseda olinadi

                    OutputsDTO dto = service.getInputCashByCreatedDate();

                    if (dto == null) {
                        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                                "Bugun hisobidan chiqimlar kiritilmagan !"));
                        return;
                    }

                    myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                            "Sana: " + dto.getCreatedDate() + "\n\n" +
                                    "\uD83D\uDCB5 Naxd chiqimlar miqdori = " + dto.getCash() + " so'm\n"
                    ));
                }

                case Constant.kun10 -> {
                    //10 kunlikni hisoblab chiqarish

                    List<OutputsDTO> dtoList = service.getInputLast10(LocalDate.now());

                    if (dtoList == null) {
                        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                                "Hozirgacha 10 kunlik hisobotlar mavjud emas ! ⚠"));
                        return;
                    }
                    for (OutputsDTO dto : dtoList) {
                        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                                "Sana: " + dto.getCreatedDate() + "\n" +
                                        "\uD83D\uDCB5 Naxd Summa: " + dto.getCash() + " so'm \n"));
                    }

                }

                case Constant.kunBuyicha -> {
                    // kiritilgan sana buyicha kirimlar
                    myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                            "\uD83D\uDCC5 Qidirmoqchi bulgan sanani kiritig: masalan (2022-12-12)",
                            Button.markup(Button.rowList(Button.row(
                                    Button.button(Constant.backToMenu)
                            )))));
                    outPuts.setStep(Step.SEARCHDAYNAXD);

                }

                case Constant.backToMenu -> {

                    menuController.outPutsMenu(message);
                    outPuts.setStep(Step.OUTPUTS);
                }
            }
            return;
        }


        if (outPuts.getStep().equals(Step.TOTALAMOUNTOUTPUTS)) {
            switch (text) {
                case Constant.bugungi -> {
                    // bugungilarni chiqramiz dataBaseda olinadi

                    OutputsDTO dto = service.getInputCashByCreatedDate();

                    if (dto == null) {
                        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                                "Bugun hisobidan chiqimlar kiritilmagan ! ❌"));
                        return;
                    }


                    myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                            "Sana: " + dto.getCreatedDate() + "\n\n" +
                                    "\uD83D\uDCB5 Bugungi umumiy chiqimlar miqdori = " + dto.getTotalAmount() +
                                    " so'm\n"
                    ));
                }

                case Constant.kun10 -> {
                    //10 kunlikni hisoblab chiqarish
                    List<OutputsDTO> dtoList = service.getInputLast10(LocalDate.now());
                    System.out.println("10");

                    if (dtoList == null) {
                        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                                "Hozirgacha 10 kunlik hisobotlar mavjud emas ! ⚠"));
                        return;
                    }
                    for (OutputsDTO dto : dtoList) {
                        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                                "Sana: " + dto.getCreatedDate() + "\n" +
                                        "\uD83D\uDCB5 Umimiy Summa: " + dto.getTotalAmount() + " so'm \n"));
                    }
                }

                case Constant.kunBuyicha -> {
                    // kiritilgan sana buyicha kirimlar

                    myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                            "\uD83D\uDCC5 Qidirmoqchi bulgan sanani kiritig: masalan (2022-12-12)",
                            Button.markup(Button.rowList(Button.row(
                                    Button.button(Constant.backToMenu)
                            )))));
                    outPuts.setStep(Step.SEARCHDAYTOTAL);
                }

                case Constant.backToMenu -> {

                    menuController.outPutsMenu(message);
                    outPuts.setStep(Step.OUTPUTS);
                }
            }
            return;
        }

        switch (outPuts.getStep()) {

            case SEARCHDAYNAXD -> {

                if (message.getText().equals(Constant.backToMenu)) {
                    menuController.naxdKirimMenu(message);
                    outPuts.setStep(Step.NAXD);
                    return;
                }

                if (sendSummaByDateNaxd(message)) {
                    outPuts.setStep(Step.NAXD);
                }

            }

            case SEARCHDAYPLASTIK -> {
                if (message.getText().equals(Constant.backToMenu)) {
                    menuController.plastikKirimMenu(message);
                    outPuts.setStep(Step.NAXD);
                    return;
                }

                if (sendSummaByDatePlastik(message)) {

                    outPuts.setStep(Step.PLASTIK);
                }
            }

            case SEARCHDAYTOTAL -> {
                if (message.getText().equals(Constant.backToMenu)) {
                    menuController.totalAmount(message);
                    outPuts.setStep(Step.NAXD);
                    return;
                }

                if (sendSummaByDateTotal(message)) {
                    outPuts.setStep(Step.TOTALAMOUNTOUTPUTS);
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


        OutputsDTO dto = service.getInputByGivenDate(date);

        if (dto == null) {
            myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                    "Kiritilgan sana boyicha chiqimlar topilmadi ! \n ❌" +
                            "🔁 Qaytadan kiriting ! "));
            return false;
        }


        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                "Sana " + dto.getCreatedDate() + "\n\n" +
                        "\uD83D\uDCB5 Naxd chiqimlar miqdori = " + dto.getCash() +
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


        OutputsDTO dto = service.getInputByGivenDate(date);

        if (dto == null) {
            myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                    "Kiritilgan sana boyicha chiqimlar topilmadi ! \n ❌" +
                            "🔁 Qaytadan kiriting ! "));
            return false;
        }


        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                "Sana " + dto.getCreatedDate() + "\n\n" +
                        "\uD83D\uDCB3 Plastik chiqimlar miqdori = " + dto.getCard() +
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


        OutputsDTO dto = service.getInputByGivenDate(date);

        if (dto == null) {
            myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                    "Kiritilgan sana boyicha chiqimlar topilmadi ! \n ❌" +
                            "🔁 Qaytadan kiriting ! "));
            return false;
        }


        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                "Sana " + dto.getCreatedDate() + "\n\n" +
                        "\uD83D\uDCB5 kundagi umimiy chiqimlar miqdori = " + dto.getTotalAmount() +
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
