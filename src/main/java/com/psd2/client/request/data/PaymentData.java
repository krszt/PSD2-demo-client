package com.psd2.client.request.data;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PaymentData {

    private String debtorIban;
    private String ultimateDebtor;
    private String currency;
    private String amount;

    private String creditorIban;
    private String ultimateCreditor;

    private LocalDate requestedDate;

}
