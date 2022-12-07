package com.example.owner.mainController.inputAndOutput;

import com.example.dto.InputDTO;
import com.example.owner.mainController.MainController;
import com.example.owner.mainController.MainMenuController;
import com.example.owner.service.InputsService;
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
public class InputsController {

    private final MainMenuController menuController;

    private final MyTelegramBot myTelegramBot;

    private final MainController mainController;

    private final InputsService service;

    private List<TelegramUsers> usersList = new ArrayList<>();

    @Lazy
    public InputsController( MainMenuController menuController, MyTelegramBot myTelegramBot, MainController mainController, InputsService service) {
        this.menuController = menuController;
        this.myTelegramBot = myTelegramBot;
        this.mainController = mainController;
        this.service = service;
    }


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

                    InputDTO dto = service.getInputCashByCreatedDate();

                    if (dto == null) {
                        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                                "Bugun hisobidan kirimlar kiritilmagan !"));
                        return;
                    }

                    myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                            "Sana: " + dto.getCreatedDate() + "\n\n" +
                                    "\uD83D\uDCB5 Naxd kirimlar miqdori = " + dto.getCash() + " so'm\n"
                    ));
                    inPuts.setStep(Step.NAXDIN);

                }

                case Constant.kun10 -> {
                    //10 kunlikni hisoblab chiqarish


                    List<InputDTO> dtoList =service.getInputLast10(LocalDate.now());

                    if (dtoList == null){
                        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                                "Hozirgacha 10 kunlik hisobotlar mavjud emas ! ⚠"));
                        return;
                    }
                    for (InputDTO dto : dtoList) {
                        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                                "Sana: "+dto.getCreatedDate()+"\n" +
                                        "\uD83D\uDCB5 Naxd Summa: "+dto.getCash()+" so'm \n"));
                    }


                }

                case Constant.kunBuyicha -> {
                    // kiritilgan sana buyicha kirimlar

                    myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                            "\uD83D\uDCC5 Qidirmoqchi bulgan sanani kiritig: masalan (2022-12-12)",
                            Button.markup(Button.rowList(Button.row(
                                    Button.button(Constant.backToMenu)
                            )))));
                    inPuts.setStep(Step.SEARCHDAYNAXD);
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

                    InputDTO dto = service.getInputCardByCreatedDate();

                    if (dto == null) {
                        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                                "Bugun hisobidan kirimlar kiritilmagan !"));
                        return;
                    }

                    myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                            "Sana: " + dto.getCreatedDate() + "\n\n" +
                                    "\uD83D\uDCB3 Plastik kirimlar miqdori = " + dto.getCard() + " so'm\n"
                    ));

                }

                case Constant.kun10 -> {
                    //10 kunlikni hisoblab chiqarish


                    List<InputDTO> dtoList =service.getInputLast10(LocalDate.now());

                    if (dtoList == null){
                        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                                "Hozirgacha 10 kunlik hisobotlar mavjud emas ! ⚠"));
                        return;
                    }
                    for (InputDTO dto : dtoList) {
                        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                                "Sana: "+dto.getCreatedDate()+"\n" +
                                        "\uD83D\uDCB5 Plastik Summa: "+dto.getCard()+" so'm \n"));
                    }


                }

                case Constant.kunBuyicha -> {
                    // kiritilgan sana buyicha kirimlar

                    myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                            "\uD83D\uDCC5 Qidirmoqchi bulgan sanani kiritig: masalan (2022-12-12)",
                            Button.markup(Button.rowList(Button.row(
                                    Button.button(Constant.backToMenu)
                            )))));
                    inPuts.setStep(Step.SEARCHDAYPLASTIK);
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

                    InputDTO dto = service.getInputCashByCreatedDate();

                    if (dto == null) {
                        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                                "Bugun hisobidan kirimlar kiritilmagan ! ❌"));
                        return;
                    }


                    myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                            "Sana: " + dto.getCreatedDate() + "\n\n" +
                                    "\uD83D\uDCB5 Bugungi umumiy kirimlar miqdori = " + dto.getTotalAmount() +
                                    " so'm\n"
                    ));

                }

                case Constant.kun10 -> {
                    //10 kunlikni hisoblab chiqarish

                    List<InputDTO> dtoList =service.getInputLast10(LocalDate.now());

                    if (dtoList == null){
                        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                                "Hozirgacha 10 kunlik hisobotlar mavjud emas ! ⚠"));
                        return;
                    }
                    for (InputDTO dto : dtoList) {
                        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                                "Sana: "+dto.getCreatedDate()+"\n" +
                                        "\uD83D\uDCB5 Umimiy Summa: "+dto.getTotalAmount()+" so'm \n"));
                    }

                }

                case Constant.kunBuyicha -> {
                    // kiritilgan sana buyicha kirimlar


                    myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                            "\uD83D\uDCC5 Qidirmoqchi bulgan sanani kiritig: masalan (2022-12-12)",
                            Button.markup(Button.rowList(Button.row(
                                    Button.button(Constant.backToMenu)
                            )))));
                    inPuts.setStep(Step.SEARCHDAYTOTAL);
                }

                case Constant.backToMenu -> {

                    menuController.inputsMenu(message);
                    inPuts.setStep(Step.INPUTS);
                }

            }
            return;
        }

        switch (inPuts.getStep()) {

            case SEARCHDAYNAXD -> {

                if (message.getText().equals(Constant.backToMenu)) {
                    menuController.naxdKirimMenu(message);
                    inPuts.setStep(Step.NAXDIN);
                    return;
                }

                if (sendSummaByDateNaxd(message)){
                    inPuts.setStep(Step.NAXDIN);
                }

            }

            case SEARCHDAYPLASTIK -> {
                if (message.getText().equals(Constant.backToMenu)) {
                    menuController.plastikKirimMenu(message);
                    inPuts.setStep(Step.NAXDIN);
                    return;
                }

                if (sendSummaByDatePlastik(message)){

                    inPuts.setStep(Step.PLASTIKIN);
                }
            }

            case SEARCHDAYTOTAL -> {
                if (message.getText().equals(Constant.backToMenu)) {
                    menuController.totalAmount(message);
                    inPuts.setStep(Step.NAXDIN);
                    return;
                }

                if (sendSummaByDateTotal(message)){
                    inPuts.setStep(Step.TOTALAMOUNTINPUTS);
                }
            }

        }

    }

    public boolean sendSummaByDateNaxd(Message message){

        LocalDate date = null;


        try {

            date = LocalDate.parse(message.getText());

        } catch (RuntimeException e) {
            myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                    "Iltimos sanani to'g'ri kiriting ! ❌"));
            return false;
        }


        InputDTO dto = service.getInputByGivenDate(date);

        if (dto == null) {
            myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                    "Kiritilgan sana boyicha kirimlar topilmadi ! \n ❌" +
                            "🔁 Qaytadan kiriting ! "));
            return false;
        }


        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                "Sana " + dto.getCreatedDate() + "\n\n" +
                        "\uD83D\uDCB5 Naxd kirimlar miqdori = " + dto.getCash() +
                        " so'm\n"));
        return true;
    }

    public boolean sendSummaByDatePlastik(Message message){

        LocalDate date = null;


        try {

            date = LocalDate.parse(message.getText());

        } catch (RuntimeException e) {
            myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                    "Iltimos sanani to'g'ri kiriting !"));
            return false;
        }


        InputDTO dto = service.getInputByGivenDate(date);

        if (dto == null) {
            myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                    "Kiritilgan sana boyicha kirimlar topilmadi ! \n ❌" +
                            "🔁 Qaytadan kiriting ! "));
            return false;
        }


        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                "Sana " + dto.getCreatedDate() + "\n\n" +
                        "\uD83D\uDCB3 Plastik kirimlar miqdori = " + dto.getCard() +
                        " so'm\n"));
        return true;
    }

    public boolean sendSummaByDateTotal(Message message){

        LocalDate date = null;


        try {

            date = LocalDate.parse(message.getText());

        } catch (RuntimeException e) {
            myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                    "Iltimos sanani to'g'ri kiriting !"));
            return false;
        }


        InputDTO dto = service.getInputByGivenDate(date);

        if (dto == null) {
            myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                    "Kiritilgan sana boyicha kirimlar topilmadi ! \n ❌" +
                            "🔁 Qaytadan kiriting ! "));
            return false;
        }


        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                "Sana " + dto.getCreatedDate() + "\n\n" +
                        "\uD83D\uDCB5 kundagi umimiy kirimlar miqdori = " + dto.getTotalAmount() +
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
