package com.example.nurse.service;
import com.example.dto.PatientDTO;
import com.example.entity.PatientEntity;
import com.example.enums.Status;
import com.example.nurse.payload.NurseDTO;
import com.example.repository.PatientRepository;
import com.example.step.Constant;
import com.example.telegramBot.MyTelegramBot;
import com.example.util.Button;
import com.example.util.SendMsg;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import java.io.*;
import java.time.LocalDate;
import java.util.*;

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
        entity.setStatus(Status.ACTIVE);
        entity.setCreatedDate(LocalDate.now());
        patientRepository.save(entity);

    }
    public boolean checkPhone(Message message) {

        if (!message.getText().startsWith("+998") || message.getText().length() != 13) {
            myTelegramBot.send(SendMsg.sendMsgParse(message.getChatId(),
                    "Iltimos telefon raqamni quyidagi ko'rinishda kiriting !" +
                            "*\nMasalan : +998951024055  ✅*"));
            return false;
        }
        return true;
    }
    public void enterFullName(Message message) {
        myTelegramBot.send(SendMsg.sendMsgParse(message.getChatId(), "Bemorning  *Ism va Familyasini* kiriting  ⬇️ "));
    }
    public void enterPhone(Message message) {
        myTelegramBot.send(SendMsg.sendMsgParse(message.getChatId(), "Bemorning *telefon* raqamini kiriting \n" +
                "Masalan : ( +998951024055 )  ⬇️"));
    }
    public void enterFloor(Message message) {
        myTelegramBot.send(SendMsg.sendMsgParse(message.getChatId(), "Bemorning yotgan *qavatni* kiriting \n" +
                "Masalan : ( 1-qavat )  ⬇️"));
    }
    public void enterHouse(Message message) {
        myTelegramBot.send(SendMsg.sendMsgParse(message.getChatId(), "Bemorning yotgan *xona* raqamini kiriting \n" +
                "Masalan : ( 23-xona )  ⬇️"));
    }
    public void endPatientRegistration(Message message) {
        myTelegramBot.send(SendMsg.sendMsgParse(message.getChatId(), "*Bemor Qabul qilindi*  ✅️"));
    }
    public void searchPatientNameAndSurname(Message message) {
        myTelegramBot.send(SendMsg.sendMsgParse(message.getChatId(), "Bemorni topish uchun bemorning *Ism va Familyasini* kiriting  ⬇️ "));
    }
    public void   deletePatientById(Message message) {

            myTelegramBot.send(SendMsg.sendMsgParse(message.getChatId(), "Bemorni o'chirish uchun bemorning *ID*" +
                    "raqamini kiriting  ⬇ ( ID raqamni bilish uchun bemorlar royhati bo'limini" +
                    "  ko'rishingiz mumkun  ✅️ "));

    }
    public void nurseMenuButton(Message message) {

        myTelegramBot.send(SendMsg.sendMsgParse(message.getChatId(), " *Registratsiya Bo'limi*  ✅ : ", Button.markup(Button.rowList(Button.row(Button.button(Constant.bemorQoshish), Button.button(Constant.bemorQidirish)), Button.row(Button.button(Constant.bemorOchirish), Button.button(Constant.bemorlarRoyhati))))));
    }
    public void nurseMenuButton2(Message message) {

        myTelegramBot.send(SendMsg.sendMsgParse(message.getChatId(), "*Keyingi amalni bajarishingiz mumkun*  ✅ : ", Button.markup(Button.rowList(Button.row(Button.button(Constant.bemorQoshish), Button.button(Constant.bemorQidirish)), Button.row(Button.button(Constant.bemorOchirish), Button.button(Constant.bemorlarRoyhati))))));
    }
    public boolean handlePatient(Message message) {

        List<PatientEntity> entityList = patientRepository.getByFullNameIgnoreCase(message.getText());

        if (entityList.isEmpty()) {
            myTelegramBot.send(SendMsg.sendMsgParse(message.getChatId(), "Kechirasiz bemor topilmadi bemorlar " +
                    "*Ro'yxatini* qarab ko'ring  ❌"));
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
                        "\uD83C\uDD94  ID : " + dto.getId() + "\n\n" +
                        "➡  Ism va Familyasi : " + dto.getFullName() + "\n\n" +
                                "\uD83D\uDED7  Qavati: " + dto.getFloor() + "\n\n" +
                                "\uD83C\uDFD8  Xona raqami: " + dto.getRoom() + "\n\n" +
                                "☎  Telefon raqami: " + dto.getPhone() + "\n\n" +
                                "\uD83D\uDCC5  Kelgan kuni: " + dto.getCreated_date()));
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

        String text = message.getText();

        for (int i = 0; i < text.length(); i++) {
            if(!Character.isDigit(text.charAt(i)) || Character.isLetter(text.charAt(i)) ){
                myTelegramBot.send(SendMsg.sendMsgParse(message.getChatId(),
                        "*ID da harf yoki belgi bo'lishi mumkin emas raqam kiriting*  ❌"));
                return false;
            }
        }

        Long id = Long.valueOf(message.getText());
        Optional<PatientEntity> optional = patientRepository.findById(id);

        if (optional.isEmpty()) {
            myTelegramBot.send(SendMsg.sendMsgParse(message.getChatId(), "Kechirasiz bunaqa *ID* mavjud emas !" +
                    "boshqatan kiriting   ⬇️"));
            return false;

        } else {

            PatientEntity entity = optional.get();
            entity.setStatus(Status.BLOCK);
            patientRepository.save(entity);
            return true;

        }
    }
    public void enddeletedPatient(Message message){
        myTelegramBot.send(SendMsg.sendMsg(message.getChatId(),"Bemor o'chirildi  \uD83D\uDDD1"));
    }
    public void patientList(Message message) {

        boolean check = false;

        Iterable<PatientEntity> patientlist = patientRepository.findAllByStatus(Status.ACTIVE);

        Map<Long, Object[]> patientData = new TreeMap<Long, Object[]>();

        patientData.put(0L, new Object[]{"ID raqami ", " Ism va Familiyasi", "Telefon raqami",
                "Qavat raqami", "Xona raqami", "Registratsiya Sanasi","Status"});

        for (PatientEntity patientEntity : patientlist) {

            if (patientEntity != null) {

                check = true;

                XSSFWorkbook workbook = new XSSFWorkbook();
                XSSFSheet spreadsheet = workbook.createSheet("Bemorlar royhati");

                XSSFRow row;

                patientData.put(patientEntity.getId(), new Object[]{patientEntity.getId().toString(), patientEntity.getFullName(),
                        patientEntity.getPhone(), patientEntity.getFloor(), patientEntity.getRoom(),
                        patientEntity.getCreatedDate().toString(),patientEntity.getStatus().toString()});
                Set<Long> keyid = patientData.keySet();

                int rowid = 0;
                for (Long key : keyid) {
                    row = spreadsheet.createRow(rowid++);
                    Object[] objectArr = patientData.get(key);
                    int cellid = 0;

                    for (Object obj : objectArr) {
                        Cell cell = row.createCell(cellid++);
                        cell.setCellValue((String) obj);
                    }

                }

                try {

                    FileOutputStream out = new FileOutputStream("Bemorlar ro`yxati.xlsx");
                    workbook.write(out);
                    out.close();


                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        if (!check) {

            myTelegramBot.send(SendMsg.sendMsgParse(message.getChatId(),
                    "*Bemorlar ro'yxati mavjud emas*"
                   ));
        } else {
            try {
                InputStream inputStream = new FileInputStream("Bemorlar ro`yxati.xlsx");
                InputFile inputFile = new InputFile();
                inputFile.setMedia(inputStream, "Bemorlar ro`yxati.xlsx");

                myTelegramBot.send(SendMsg.sendpatientDoc(message.getChatId(), inputFile
                        ));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
