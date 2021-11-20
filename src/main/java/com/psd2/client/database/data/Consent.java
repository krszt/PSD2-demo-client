package com.psd2.client.database.data;

import io.quarkus.mongodb.panache.PanacheMongoEntityBase;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Slf4j
public class Consent extends PanacheMongoEntityBase {

    @BsonId
    private String consentId;

    private Account account;

    private String status;

    private LocalDate validUntil;


    @Builder
    public Consent(String consentId, Account account, String status, LocalDate validUntil) {
        this.consentId = consentId;
        this.account = account;
        this.status = status;
        this.validUntil = validUntil;
    }


    public Consent() {
        super();
    }

    public static Consent findByAccountAndStatus(String iban){

        log.info("findByAccount");
        Account account = Account.findByIban(iban);
        log.info("{}", account);
        if(account == null){
            return null;
        }

        List<Consent> consentList = find("status", "valid").list();


        if(consentList != null) {
            for (Consent consent : consentList) {

                log.info("consent iban: {}", consent.account.getIban());
                log.info("account iban: {}", account.getIban());

                if (consent.account.getIban().equals(account.getIban())) {
                    log.info("consentreturned: {}", consent);
                    return consent;
                }
            }
        }

        return null;

    }

    public static Consent findByConsentId(String consentId){
        return find("_id", consentId).firstResult();

    }

    public static Consent findByStatus(){
        return find("status", "valid").firstResult();

    }

}
