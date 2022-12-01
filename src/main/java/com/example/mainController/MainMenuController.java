package com.example.mainController;

import com.example.step.Constant;
import com.example.telegramBot.MyTelegramBot;
import com.example.util.Button;
import com.example.util.SendMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.objects.Message;

@Controller
public class MainMenuController {


    @Autowired
    private MyTelegramBot myTelegramBot;


    public void mainMenu(Message message) {


        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                "Asosiy Menyuga Xush kelibsiz",
                Button.markup(Button.rowList(Button.row(
                        Button.button(Constant.kirim), Button.button(Constant.chiqim)
                ), Button.row(
                        Button.button(Constant.qoldiq), Button.button(Constant.bemorQidirish)
                ), Button.row(
                        Button.button(Constant.bemorlarSoni), Button.button(Constant.adminMenu)
                )))));
    }

    public void inputsMenu(Message message) {
        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                "Kirimlar Menyusi",
                Button.markup(Button.rowList(Button.row(
                                Button.button(Constant.naxd),
                                Button.button(Constant.plastik),
                                Button.button(Constant.umumiyBlance)
                        ),
                        Button.row(
                                Button.button(Constant.backToMenu)
                        )))));
    }


    public void naxdKirimMenu(Message message) {
        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                "Naxd usulidan Kirimlar ",
                Button.markup(Button.rowList(Button.row(
                                Button.button(Constant.bugungi),
                                Button.button(Constant.kun10)
                        ),
                        Button.row(Button.button(Constant.kunBuyicha)),
                        Button.row(Button.button(Constant.backToMenu)
                        )))));
    }

    public void outPutsMenu(Message message) {
        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                "Chiqimlar Menyusi",
                Button.markup(Button.rowList(Button.row(
                                Button.button(Constant.naxd),
                                Button.button(Constant.plastik),
                                Button.button(Constant.umumiyBlance)
                        ),
                        Button.row(
                                Button.button(Constant.backToMenu)
                        )))));
    }

    public void plastikKirimMenu(Message message) {
        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                "Plastik usulidan Kirimlar ",
                Button.markup(Button.rowList(Button.row(
                                Button.button(Constant.bugungi),
                                Button.button(Constant.kun10)
                        ),
                        Button.row(Button.button(Constant.kunBuyicha)),
                        Button.row(Button.button(Constant.backToMenu)
                        )))));
    }

    public void naxdMenuChiqim(Message message) {
        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                "Naxd usulidan Chiqimlar Menyusi ",
                Button.markup(Button.rowList(Button.row(
                                Button.button(Constant.bugungi),
                                Button.button(Constant.kun10)
                        ),
                        Button.row(Button.button(Constant.kunBuyicha)),
                        Button.row(Button.button(Constant.backToMenu)
                        )))));
    }

    public void plastikChiqimMenu(Message message) {
        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                "Plastik usulidan Chiqimlar Menyusi",
                Button.markup(Button.rowList(Button.row(
                                Button.button(Constant.bugungi),
                                Button.button(Constant.kun10)
                        ),
                        Button.row(Button.button(Constant.kunBuyicha)),
                        Button.row(Button.button(Constant.backToMenu)
                        )))));
    }

    public void totalAmount(Message message) {
        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                "Umimiy usulidan Kirimlar Menyusi ",
                Button.markup(Button.rowList(Button.row(
                                Button.button(Constant.bugungi),
                                Button.button(Constant.kun10)
                        ),
                        Button.row(Button.button(Constant.kunBuyicha)),
                        Button.row(Button.button(Constant.backToMenu)
                        )))));
    }
    public void totalAmountOutputs(Message message) {
        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                "Umimiy usulidan Chiqimlar Menyusi ",
                Button.markup(Button.rowList(Button.row(
                                Button.button(Constant.bugungi),
                                Button.button(Constant.kun10)
                        ),
                        Button.row(Button.button(Constant.kunBuyicha)),
                        Button.row(Button.button(Constant.backToMenu)
                        )))));
    }
    public void qoldiqMenu(Message message) {
        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                "Qoldiqlar Menyusi",
                Button.markup(Button.rowList(Button.row(
                                Button.button(Constant.naxd),
                                Button.button(Constant.plastik),
                                Button.button(Constant.umumiyBlance)
                        ),
                        Button.row(
                                Button.button(Constant.backToMenu)
                        )))));
    }
    public void naxqQoldilar(Message message) {
        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                "Naxq usulidan Chiqimlar Menyusi ",
                Button.markup(Button.rowList(Button.row(
                                Button.button(Constant.bugungi),
                                Button.button(Constant.kun10)
                        ),
                        Button.row(Button.button(Constant.kunBuyicha)),
                        Button.row(Button.button(Constant.backToMenu)
                        )))));
    }
    public void plastikQoldilar(Message message) {
        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                "Plastik usulidan Chiqimlar Menyusi ",
                Button.markup(Button.rowList(Button.row(
                                Button.button(Constant.bugungi),
                                Button.button(Constant.kun10)
                        ),
                        Button.row(Button.button(Constant.kunBuyicha)),
                        Button.row(Button.button(Constant.backToMenu)
                        )))));
    }
    public void totalResidualOutputs(Message message) {
        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                "Umimiy usulidan Qoldiqlar Menyusi ",
                Button.markup(Button.rowList(Button.row(
                                Button.button(Constant.bugungi),
                                Button.button(Constant.kun10)
                        ),
                        Button.row(Button.button(Constant.kunBuyicha)),
                        Button.row(Button.button(Constant.backToMenu)
                        )))));
    }
    public void countSick(Message message) {
        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                "Qavatni kiriting",
                Button.markup(Button.rowList(Button.row(
                                        Button.button(Constant.qavat_2),
                                        Button.button(Constant.qavat_3)
                                ),
                                Button.row(Button.button(Constant.backToMenu)))
                )));
    }

    public void addAdminMenu(Message message) {
        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                "Admin qushish Menyusi",
                Button.markup(Button.rowList(
                        Button.row(Button.button(Constant.addAdmin),
                                Button.button(Constant.removeAdmin)),
                        Button.row(Button.button(Constant.backToMenu))
                ))));
    }




}
