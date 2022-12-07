package com.example.owner;

import java.time.LocalDate;

public interface ProfitMapper {

    Double getCard();

    Double getCash();

    Double getTotal();


    LocalDate getCreatedDate();
}
