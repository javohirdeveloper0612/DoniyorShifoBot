package com.example.nurse.service;
import com.example.dto.PatientDTO;
import com.example.entity.PatientEntity;
import com.example.enums.PatientStatus;
import com.example.nurse.payload.NurseDTO;
import com.example.repository.PatientRepository;
import com.example.step.Constant;
import com.example.telegramBot.MyTelegramBot;
import com.example.util.Button;
import com.example.util.SendMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class NurseService {

    @Autowired
    private PatientRepository patientRepository;
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
        patientRepository.save(entity);

    }
    public boolean checkPhone(Message message) {
        if (!message.getText().startsWith("998") && message.getText().length() != 12) {
            myTelegramBot.send(SendMsg.sendMsgParse(message.getChatId(),
                    "Iltimos telefon raqamni quyidagi ko'rinishda kiriting !" +
                            "*\nMasalan : 998951024055  ✅*"));
            return false;
        }
        return true;
    }
    public void enterFullName(Message message) {
        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(), "Bemorning  Ism va Familyasini kiriting  ⬇️ "));
    }
    public void enterPhone(Message message) {
        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(), "Bemorning telefon raqamini kiriting : ( 99 89? ??? ?? ?? )  ⬇️"));
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
    public void  deletePatientById(Message message) {
        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(), "Bemorni o'chirish uchun bemorning ID" +
                "raqamini kiriting  ⬇ ( ID raqamni bilish uchun bemorlar royhati bo'limini" +
                "  ko'rishingiz mumkun  ✅️ "));
    }
    public void patientRoyxati(Message message) {
        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(), "Doniyor Shifo Klinikasidagi bemorlar ro'yxati  ⬇️ "));
    }
    public void nurseMenuButton(Message message) {

        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(), " Registratsiya Bo'limi  ✅ : ", Button.markup(Button.rowList(Button.row(Button.button(Constant.bemorQoshish), Button.button(Constant.bemorQidirish)), Button.row(Button.button(Constant.bemorOchirish), Button.button(Constant.bemorlarRoyhati))))));
    }
    public void nurseMenuButton2(Message message) {

        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(), "Keyingi amalni bajarishingiz mumkun  ✅ : ", Button.markup(Button.rowList(Button.row(Button.button(Constant.bemorQoshish), Button.button(Constant.bemorQidirish)), Button.row(Button.button(Constant.bemorOchirish), Button.button(Constant.bemorlarRoyhati))))));
    }
    public boolean handlePatient(Message message) {

        List<PatientEntity> entityList = patientRepository.getByFullNameIgnoreCase(message.getText());

        if (entityList.isEmpty()) {
            myTelegramBot.send(SendMsg.sendMsg(message.getChatId(), "Kechirasiz bemor topilmadi Bunaqa " +
                    "bemor bemorlar royhatida  mavjud emas bemorlar ro'yhatini qarab ko'ring ❌"));
            return false;
        } else {

            List<NurseDTO> dtoList = new LinkedList<>();


            for (PatientEntity entity : entityList) {
                NurseDTO nurseDTO = new NurseDTO();
                nurseDTO.setId(entity.getId());
                nurseDTO.setFullName(entity.getFullName());
                nurseDTO.setPhone(entity.getPhone());
                nurseDTO.setFloor(entity.getFloor());
                nurseDTO.setRoom(entity.getRoom());
                nurseDTO.setCreated_date(entity.getCreatedDate());
                dtoList.add(nurseDTO);
            }

            for (NurseDTO dto : dtoList) {
                myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),
                        "Ismi: " + dto.getFullName() + "\n\n" +
                                "\uD83D\uDED7 Qavati: " + dto.getFloor() + "\n\n" +
                                "\uD83C\uDFD8 Xona raqami: " + dto.getRoom() + "\n\n" +
                                "☎ Telefon raqami: " + dto.getPhone() + "\n\n" +
                                "\uD83D\uDCC5 Kelgan kuni: " + dto.getCreated_date()));
            }

        }
        return true;
    }
    public PatientDTO toDTO(PatientEntity entity) {
        PatientDTO dto = new PatientDTO();
        dto.setId(entity.getId());
        dto.setFullName(entity.getFullName());
        dto.setPhone(entity.getPhone());
        dto.setFloor(entity.getFloor());
        dto.setRoom(entity.getRoom());
        dto.setStatus(entity.getStatus());
        dto.setCreatedDate(entity.getCreatedDate());


        return dto;
    }
    public boolean deletedById(Message message) {

        Long id = Long.valueOf(message.getText());
        Optional<PatientEntity> optional = patientRepository.findById(id);

        if (optional.isEmpty()) {
            myTelegramBot.send(SendMsg.sendMsg(message.getChatId(), "Kechirasiz bunaqa ID mavjud emas !" +
                    "boshqatan kiriting   ⬇️"));
            return false;

        } else {

            PatientEntity entity = optional.get();
            entity.setStatus(PatientStatus.BLOCK);
            patientRepository.save(entity);
            return true;

        }
    }
    public void enddeletedPatient(Message message){
        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),"Bemor o'chirildi  \uD83D\uDDD1"));
    }
}
