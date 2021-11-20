package com.psd2.client.request.data;

import com.psd2.client.database.data.Account;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ConsentData {

    private String iban;

    private LocalDate validUntil;

    private String frequencyPerDay;

    private boolean recurringIndicator;

    private Account account;

    private String pinCode;


}
