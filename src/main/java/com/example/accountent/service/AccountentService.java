package com.example.accountent.service;

import com.example.entity.InputEntity;
import com.example.entity.OutputEntity;
import com.example.repository.InputsRepository;
import com.example.repository.OutputRepository;
import com.example.step.Constant;
import com.example.telegramBot.MyTelegramBot;
import com.example.util.Button;
import com.example.util.SendMsg;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class AccountentService {

    private final MyTelegramBot myTelegramBot;
    private final InputsRepository inputsRepository;
    private final OutputRepository outputRepository;


    public AccountentService(MyTelegramBot myTelegramBot, InputsRepository inputsRepository, OutputRepository outputRepository) {
        this.myTelegramBot = myTelegramBot;
        this.inputsRepository = inputsRepository;
        this.outputRepository = outputRepository;
    }

    public void addChiqimPlastik(Message message) {
        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                "Bugungi chiqim summasini kiriting : (Plastik)"));
    }

    public void addChiqimNaxd(Message message) {
        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                "Bugungi chiqim summasini  kiriting (Naqt) :"));
    }

    public void addKidrimPlastik(Message message) {
        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                "Bugungi kirim summasini kiriting  (Plastik) :"));
    }

    public void addKirimNaxd(Message message) {
        myTelegramBot.send(SendMsg.sendMsgParse(message.getChatId(),
                "*Bugungi kirim summasini kiriting  (Naqd)*"));
    }

    public void addInputMenu(Message message) {
        myTelegramBot.send(SendMsg.sendMsgParse(message.getChatId(),
                "*Kiririmlarni kiritish*",
                Button.markup(Button.rowList(Button.row(
                                Button.button(Constant.naxd),
                                Button.button(Constant.plastik)),
                        Button.row(
                                Button.button(Constant.backToMenu)
                        )))));

    }

    public void addOutputMenu(Message message) {
        myTelegramBot.send(SendMsg.sendMsgParse(message.getChatId(),
                "*Chiqimlarni kiritish*",
                Button.markup(Button.rowList(Button.row(
                                Button.button(Constant.naxd),
                                Button.button(Constant.plastik)),
                        Button.row(
                                Button.button(Constant.backToMenu)
                        )))));

    }

    public boolean saveCash(Message message) {

        String text = message.getText();
        double sum = 0.;

        for (int i = 0; i < text.length(); i++) {
            if (!Character.isDigit(text.charAt(i))) {
                myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                        "Kiritilgan summa faqat raqamlardan iborat bo'lshi kerak !"));
                return false;
            }
        }

        sum = Double.parseDouble(text.trim());

        if (sum > 0) {
            naxdKirim(sum);
            myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                    "Muvaffaqqiyatli saqlandi ✅✅"));
            return true;

        } else {
            myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                    "Kiritilgan malumotlar  xato . Miqdorlarni faqat raqamlar bilan ifodalang"));
            return false;
        }
    }

    private void naxdKirim(Double sum) {


        Optional<InputEntity> byCreatedDate = inputsRepository.findByCreatedDate(LocalDate.now());
        if (byCreatedDate.isEmpty()) {
            InputEntity inputEntity = new InputEntity();
            inputEntity.setCash(sum);
            inputEntity.setTotalAmount(sum);
            inputsRepository.save(inputEntity);
            return;
        }
        InputEntity byDay = byCreatedDate.get();
        if (byDay.getCash() == null) {
            byDay.setCash(sum);
        } else {
            byDay.setCash(byDay.getCash() + sum);
        }

        byDay.setTotalAmount(byDay.getTotalAmount() + sum);
        inputsRepository.save(byDay);

    }


    public boolean saveCard(Message message) {

        String text = message.getText();
        double sum = 0;

        for (int i = 0; i < text.length(); i++) {
            if (!Character.isDigit(text.charAt(i))) {
                myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                        "Kiritilgan summa faqat raqamlardan  iborat bo'lshi kerak !"));
                return false;
            }
        }

        sum = Double.parseDouble(text.trim());

        if (sum > 0) {
            plastikKirim(sum);
            myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                    "Muvaffaqqiyatli saqlandi ✅✅"));
            return true;

        } else {
            myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                    "Kiritilgan ma'lumotlar  xato . Miqdorlarni faqat raqamlar bilan ifodalang !!"));
            return false;
        }

    }

    private void plastikKirim(double sum) {

        Optional<InputEntity> byCreatedDate = inputsRepository.findByCreatedDate(LocalDate.now());

        if (byCreatedDate.isEmpty()) {
            InputEntity inputEntity = new InputEntity();
            inputEntity.setCard(sum);
            inputEntity.setTotalAmount(sum);
            inputsRepository.save(inputEntity);
            return;
        }
        InputEntity byDay = byCreatedDate.get();

        if (byDay.getCard() == null) {
            byDay.setCard(sum);
        } else {
            byDay.setCard(byDay.getCard() + sum);
        }
        byDay.setTotalAmount(byDay.getTotalAmount() + sum);
        inputsRepository.save(byDay);
    }

    public boolean saveCashOutput(Message message) {
        String text = message.getText();
        double sum = 0.;

        for (int i = 0; i < text.length(); i++) {
            if (!Character.isDigit(text.charAt(i))) {
                myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                        "Kiritilgan summa faqat raqamlardan iborat bo'lshi kerak !"));
                return false;
            }
        }

        sum = Double.parseDouble(text.trim());

        if (sum > 0) {
            naxtChiqim(sum);
            myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                    "Muvaffaqqiyatli saqlandi ✅✅"));
            return true;

        } else {
            myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                    "Kiritilgan malumotlar  xato . Miqdorlarni faqat raqamlar bilan ifodalang"));
            return false;
        }
    }

    private void naxtChiqim(double sum) {
        Optional<OutputEntity> byCreatedDate = outputRepository.findByCreatedDate(LocalDate.now());

        if (byCreatedDate.isEmpty()) {
            OutputEntity outputEntity = new OutputEntity();
            outputEntity.setCash(sum);
            outputEntity.setTotalAmount(sum);
            outputRepository.save(outputEntity);
            return;
        }
        OutputEntity byDay = byCreatedDate.get();

        if (byDay.getCash() == null) {
            byDay.setCash(sum);
        } else {
            byDay.setCash(byDay.getCash() + sum);
        }
        byDay.setTotalAmount(byDay.getTotalAmount() + sum);
        outputRepository.save(byDay);
    }

    public boolean saveCardOutput(Message message) {
        String text = message.getText();
        double sum = 0.;

        for (int i = 0; i < text.length(); i++) {
            if (!Character.isDigit(text.charAt(i))) {
                myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                        "Kiritilgan summa faqat raqamlardan iborat bo'lshi kerak !"));
                return false;
            }
        }

        sum = Double.parseDouble(text.trim());

        if (sum > 0) {
            cardOutput(sum);
            myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                    "Muvaffaqqiyatli saqlandi ✅✅"));
            return true;

        } else {
            myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                    "Kiritilgan malumotlar  xato . Miqdorlarni faqat raqamlar bilan ifodalang"));
            return false;
        }
    }

    private void cardOutput(double sum) {
        Optional<OutputEntity> byCreatedDate = outputRepository.findByCreatedDate(LocalDate.now());

        if (byCreatedDate.isEmpty()) {
            OutputEntity outputEntity = new OutputEntity();
            outputEntity.setCard(sum);
            outputEntity.setTotalAmount(sum);
            outputRepository.save(outputEntity);
            return;
        }
        OutputEntity byDay = byCreatedDate.get();

        if (byDay.getCard() == null) {
            byDay.setCard(sum);
        } else {
            byDay.setCard(byDay.getCard() + sum);
        }
        byDay.setTotalAmount(byDay.getTotalAmount() + sum);
        outputRepository.save(byDay);
    }
}

