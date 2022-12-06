package com.example.nurse;
import com.example.entity.PatientEntity;
import com.example.enums.PatientStatus;
import com.example.step.Constant;
import com.example.telegramBot.MyTelegramBot;
import com.example.util.Button;
import com.example.util.SendMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.time.LocalDate;

@Service
public class NurseService {

    @Autowired
    private NurseRepostoriy nurseRepostoriy;
    @Autowired
    private MyTelegramBot myTelegramBot;

    public void creationPatient(NurseDTO dto) {

        PatientEntity entity = new PatientEntity();
        entity.setFullName(dto.getFullName());
        entity.setPhone(dto.getPhone());
        entity.setFloor(dto.getFloor());
        entity.setRoom(dto.getRoom());
        entity.setStatus(PatientStatus.ACTIVE);
        entity.setCreatedDate(LocalDate.now());
        nurseRepostoriy.save(entity);
    }

    public void enterFullName(Message message) {
        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(), "Bemorning  Ism va Familyasini kiriting  ⬇️ "));
    }

    public void enterPhone(Message message) {
        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(), "Bemorning telefon raqamini kiriting : ( +99 89? ??? ?? ?? )  ⬇️"));
    }

    public void enterFloor(Message message) {
        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(), "Bemorning honasini qavat raqamini kiriting  ⬇️"));
    }

    public void enterHouse(Message message) {
        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(), "Bemorning hona raqamini kiriting  ⬇️"));
    }

    public void endPatientRegistration(Message message) {
        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(), "Bemor Qabul qilindi  ✅️"));
    }

    public void searchPatientNameAndSurname(Message message) {
        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(), "Bemorni topish uchun bemorning Ism va Familyasini kiriting  ⬇️ "));
    }

    public void deletePatientNameAndSurname(Message message) {
        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(), "Bemorni o'chirish uchun bemorning Ism va Familyasini kiriting  ⬇️ "));
    }

    public void patientRoyxati(Message message) {
        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(), "Doniyor Shifo Klinikasidagi bemorlar ro'yxati  ⬇️ "));
    }


    public void nurseMenuButton(Message message) {

        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(), "Asalom Alaykum Hamshira Bo'limiga xush kelibsiz : ", Button.markup(Button.rowList(Button.row(Button.button(Constant.bemorQoshish), Button.button(Constant.bemorQidirish)), Button.row(Button.button(Constant.bemorOchirish), Button.button(Constant.bemorlarRoyhati))))));
    }

    public void nurseMenuButton2(Message message) {

        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(), "Keyingi amalni bajarishingiz mumkun  ✅ : ", Button.markup(Button.rowList(Button.row(Button.button(Constant.bemorQoshish), Button.button(Constant.bemorQidirish)), Button.row(Button.button(Constant.bemorOchirish), Button.button(Constant.bemorlarRoyhati))))));
    }

}
