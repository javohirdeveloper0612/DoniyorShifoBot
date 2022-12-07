package com.example.mainController;

import com.example.dto.InputDTO;
import com.example.dto.ProfitDTO;
import com.example.service.ProfitService;
import com.example.step.Constant;
import com.example.step.Step;
import com.example.step.TelegramUsers;
import com.example.telegramBot.MyTelegramBot;
import com.example.util.SendMsg;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ProfitController {


    private final MainMenuController menuController;


    private final MainController mainController;

    private final ProfitService service;

    private final MyTelegramBot myTelegramBot;

    @Lazy
    private List<TelegramUsers> usersList = new ArrayList<>();

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
                    ProfitDTO dto = service.getTodayProfit();

                    if (dto == null) {
                        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                                "Bugun hisobidan kirimlar kiritilmagan ! ❌"));
                        return;
                    }


                    myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                            "Sana: " + dto.getCreated_date() + "\n\n" +
                                    "\uD83D\uDCB5 Bugungi umumiy kirimlar miqdori = " + dto.getSum() +
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
                    residual.setStep(Step.RESIDUAL);
                }
            }
        }

        if (residual.getStep().equals(Step.PLASTIK)) {
            switch (text) {
                case Constant.bugungi -> {
                    // bugungilarni chiqramiz dataBaseda olinadi

                }

                case Constant.kun10 -> {
                    //10 kunlikni hisoblab chiqarish
                }

                case Constant.kunBuyicha -> {
                    // kiritilgan sana buyicha kirimlar
                }

                case Constant.backToMenu -> {

                    menuController.inputsMenu(message);
                    residual.setStep(Step.RESIDUAL);
                }
            }
        }

        if (residual.getStep().equals(Step.TOTALAMOUNTINPUTS)) {
            switch (text) {
                case Constant.bugungi -> {
                    // bugungilarni chiqramiz dataBaseda olinadi
                }

                case Constant.kun10 -> {
                    //10 kunlikni hisoblab chiqarish
                }

                case Constant.kunBuyicha -> {
                    // kiritilgan sana buyicha kirimlar
                }

                case Constant.backToMenu -> {

                    menuController.inputsMenu(message);
                    residual.setStep(Step.RESIDUAL);
                }
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
