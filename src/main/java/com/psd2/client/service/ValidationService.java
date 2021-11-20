package com.psd2.client.service;


import com.psd2.client.database.data.Account;
import com.psd2.client.database.data.Consent;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.core.Response;

@Slf4j
@ApplicationScoped
public class ValidationService {


    public boolean consentCheck(String consentId){
        return Consent.findById(consentId) != null;
    }


    public String fullConsentCheck(String accountId){
        Account account = Account.findByAccountId(accountId);

        if(account == null){
            return null;
        }

        Consent consent = Consent.findByAccountAndStatus(account.getIban());

        if(consent == null){
            return null;
        }

        return consent.getConsentId();

    }


}
