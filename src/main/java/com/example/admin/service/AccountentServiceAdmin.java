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
public class AccountentServiceAdmin {

    private final MyTelegramBot myTelegramBot;
    private final UsersRepository usersRepository;

    public AccountentServiceAdmin(MyTelegramBot myTelegramBot, UsersRepository usersRepository) {
        this.myTelegramBot = myTelegramBot;
        this.usersRepository = usersRepository;
    }

    public void getAccountentFullName(Message message) {
        myTelegramBot.send(SendMsg.sendMsgParse(message.getChatId(),
                "Iltimos Kassirning ismi va familiyasini quyidagi ko'rinishida kiriting\n" +
                        "*Masalan : Ali Aliyev*"));
    }

    public void getAccountentPhone(Message message) {
        myTelegramBot.send(SendMsg.sendMsgParse(message.getChatId(),
                "Iltimos Kassirning telefon raqamini quyidagi ko'rinishda kiriting\n" +
                        "*Masalan : +998971234567*"));
    }


    public void getAccountentPassword(Message message) {
        myTelegramBot.send(SendMsg.sendMsgParse(message.getChatId(),
                "Iltimos kassir ushbu botdan foydalanish uchun parol o'rnating !" +
                        "\n*Eslatma : parol takrorlanmas bo'lish kerak*"));
    }

    public void saveAccountent(Message message) {
        myTelegramBot.send(SendMsg.sendMsgParse(message.getChatId(),
                "*Muvafaqqiyatli saqlandi ✅✅ *",
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
                "*Bunday parol ba'zada mavjud. Iltimos boshqa parol o'rnating !*"));
    }

    public void exisByPhone(Message message) {
        myTelegramBot.send(SendMsg.sendMsgParse(message.getChatId(),
                "*Ushbu telefon nomer ba'zada mavjud !*"));
    }


    public void getAccountentId(Message message) {
        myTelegramBot.send(SendMsg.sendMsgParse(message.getChatId(),
                "Iltimos Hamshiraning ID raqamini kiriting " +
                        "\n*Eslatma : ID raqamni  Kassirlar ro'yxatidan olishingiz mumkin !*"));
    }


    public boolean checkAccountentId(Message message) {

        String text = message.getText();
        for (int i = 0; i < text.length(); i++) {
            if (!Character.isDigit(text.charAt(i)) || Character.isLetter(text.charAt(i))) {
                myTelegramBot.send(SendMsg.sendMsgParse(message.getChatId(),
                        "*ID da harf yoki belgi bo'lishi mumkin emas !*"));
                return false;
            }
        }

        Long id = Long.valueOf(message.getText());
        Optional<UsersEntity> optionalUsers = usersRepository.findById(id);
        if (optionalUsers.isEmpty()) {
            myTelegramBot.send(SendMsg.sendMsgParse(message.getChatId(),
                    "*Ushbu ID li Kassir mavjud emas !*"));
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

    public void accountentList(Message message) {
        boolean check = false;

        Iterable<UsersEntity> accountentList = usersRepository.findAllByRole(UserRole.ACCOUNTENT);

        Map<Long, Object[]> accountData = new TreeMap<Long, Object[]>();
        accountData.put(0L, new Object[]{"ID raqami ", "Kassirning ismi va familiyasi", "Kassirning telefon raqami",
                "Parol", "Lavozimi", "Holati"});

        for (UsersEntity accountent : accountentList) {
            if (accountent != null) {
                check = true;
                XSSFWorkbook workbook = new XSSFWorkbook();
                XSSFSheet spreadsheet = workbook.createSheet("Kassirlar ro`yxati");
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

                    FileOutputStream out = new FileOutputStream("kassirlar ro`yxati.xlsx");
                    workbook.write(out);
                    out.close();


                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        if (!check) {

            myTelegramBot.send(SendMsg.sendMsgParse(message.getChatId(),
                    "*Kassirlar ro'yxati mavjud emas*",
                    Button.markup(Button.rowList(Button.row(Button.button(Constant.backToMenu))))));
        } else {
            try {
                InputStream inputStream = new FileInputStream("kassirlar ro`yxati.xlsx");
                InputFile inputFile = new InputFile();
                inputFile.setMedia(inputStream, "kassirlar ro`yxati.xlsx");

                myTelegramBot.send(SendMsg.sendDoc(message.getChatId(), inputFile,
                        Button.markup(Button.rowList(Button.row(Button.button(Constant.backToMenu))))));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
