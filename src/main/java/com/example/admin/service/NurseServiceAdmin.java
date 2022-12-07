package com.example.admin.service;


import com.example.entity.UsersEntity;
import com.example.enums.Status;
import com.example.enums.UserRole;
import com.example.owner.repository.UsersRepository;
import com.example.step.Constant;
import com.example.telegramBot.MyTelegramBot;
import com.example.util.Button;
import com.example.util.SendMsg;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.*;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;

@Service
public class NurseServiceAdmin {

    private final MyTelegramBot myTelegramBot;

    private final UsersRepository usersRepository;


    public NurseServiceAdmin(MyTelegramBot myTelegramBot, UsersRepository usersRepository) {
        this.myTelegramBot = myTelegramBot;

        this.usersRepository = usersRepository;

    }


    public void getNurseFullName(Message message) {
        myTelegramBot.send(SendMsg.sendMsgParse(message.getChatId(),
                "Iltimos Hamshiraning ismi va familiyasini quyidagi shakilda kiriting " +
                        "\n*Masalan : Abdullayeva Maftuna*"));
    }


    public void getNursePhone(Message message) {
        myTelegramBot.send(SendMsg.sendMsgParse(message.getChatId(),
                "Iltimos Hamshiraning telefon raqamini quyidagi shakilda kiriting" +
                        "\n*Masalan : +998971234567*"));
    }

    public void getNursePassword(Message message) {
        myTelegramBot.send(SendMsg.sendMsgParse(message.getChatId(),
                "Iltimos hamshira ushbu bot dan foydalanish uchun parol o'rnating " +
                        "\n*Eslatma : Parol takrorlanmas bo'lish kerak !*"));
    }


    public void saveNurse(Message message) {
        myTelegramBot.send(SendMsg.sendMsgParse(message.getChatId(),
                "*Muvaffaqqiyatli saqlandi ✅✅ *",
                Button.markup(Button.rowList(Button.row(Button.button(Constant.backToMenu))))));
    }

    public boolean checkPhone(Message message) {
        if (!message.getText().startsWith("+998") || message.getText().length() != 13) {
            myTelegramBot.send(SendMsg.sendMsgParse(message.getChatId(),
                    "Iltimos telefon raqamni quyidagi ko'rinishda kiriting !" +
                            "*\nMasalan : +998971234567*"));
            return false;
        }
        return true;
    }

    public void checkpassword(Message message) {
        myTelegramBot.send(SendMsg.sendMsgParse(message.getChatId(),
                "*Bunday parol bazada mavjud. Iltimos boshqa parol kiriting !*"));
    }

    public void exisByPhone(Message message) {
        myTelegramBot.send(SendMsg.sendMsgParse(message.getChatId(),
                "*Ushbu telefon nomer ba'zada mavjud !*"));
    }


    public void getNurseId(Message message) {
        myTelegramBot.send(SendMsg.sendMsgParse(message.getChatId(),
                "Iltimos Hamshiraning ID raqamini kiriting " +
                        "\n*Eslatma : ID raqamni  Hamshiralar ro'yxatidan olishingiz mumkin !*"));
    }


    public boolean checkNurseId(Message message) {

        String text = message.getText();
        for (int i = 0; i < text.length(); i++) {
            if (!Character.isDigit(text.charAt(i)) || Character.isLetter(text.charAt(i))) {
                myTelegramBot.send(SendMsg.sendMsgParse(message.getChatId(),
                        "*ID raqamda harf yoki belgi bo'lishi mumkin emas !*"));
                return false;
            }
        }

        Long id = Long.valueOf(message.getText());
        Optional<UsersEntity> optionalUsers = usersRepository.findById(id);
        if (optionalUsers.isEmpty()) {
            myTelegramBot.send(SendMsg.sendMsgParse(message.getChatId(),
                    "*Ushbu ID li Hamshira mavjud emas !*"));
            return false;
        } else {
            UsersEntity usersEntity = optionalUsers.get();
            if (!usersEntity.getStatus().equals(Status.BLOCK)) {
                usersEntity.setStatus(Status.BLOCK);
                usersRepository.save(usersEntity);
                myTelegramBot.send(SendMsg.sendMsgParse(message.getChatId(),
                        "*Muvaffaqqiyatli o'chirildi ✅✅*",
                        Button.markup(Button.rowList(Button.row(Button.button(Constant.backToMenu))))));
                return true;
            } else {
                myTelegramBot.send(SendMsg.sendMsgParse(message.getChatId(),
                        "*Ushbu ID li Hamshira mavjud emas !*"));
                return false;
            }
        }

    }

    public void nurseList(Message message) {
        boolean check = false;

        Iterable<UsersEntity> nurseList = usersRepository.findAllByRole(UserRole.NURSE);

        Map<Long, Object[]> accountData = new TreeMap<Long, Object[]>();
        accountData.put(0L, new Object[]{"ID raqami ", "Ismi va familiyasi", "Telefon raqami",
                "Parol", "Lavozimi", "Holati"});

        for (UsersEntity accountent : nurseList) {
            if (accountent != null) {
                check = true;
                XSSFWorkbook workbook = new XSSFWorkbook();
                XSSFSheet spreadsheet = workbook.createSheet("Xamshiralar ro`yxati");
                XSSFRow row;
                accountData.put(accountent.getId(), new Object[]{accountent.getId().toString(), accountent.getFullName(),
                        accountent.getPhone(), accountent.getPassword(), accountent.getRole().toString(), accountent.getStatus().toString()});
                Set<Long> keyid = accountData.keySet();

                int rowid = 0;
                for (Long key : keyid) {
                    row = spreadsheet.createRow(rowid++);
                    Object[] objectArr = accountData.get(key);
                    int cellid = 0;

                    for (Object obj : objectArr) {
                        Cell cell = row.createCell(cellid++);
                        cell.setCellValue((String) obj);
                    }

                }

                try {

                    FileOutputStream out = new FileOutputStream("xamshiralar ro`yxati.xlsx");
                    workbook.write(out);
                    out.close();


                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        if (!check) {

            myTelegramBot.send(SendMsg.sendMsgParse(message.getChatId(),
                    "*Xamshiralar ro'yxati mavjud emas*",
                    Button.markup(Button.rowList(Button.row(Button.button(Constant.backToMenu))))));
        } else {
            try {
                InputStream inputStream = new FileInputStream("xamshiralar ro`yxati.xlsx");
                InputFile inputFile = new InputFile();
                inputFile.setMedia(inputStream, "xamshiralar ro`yxati.xlsx");

                myTelegramBot.send(SendMsg.sendDoc(message.getChatId(), inputFile,
                        Button.markup(Button.rowList(Button.row(Button.button(Constant.backToMenu))))));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
