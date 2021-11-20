package com.psd2.client.database.data;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BankAccount extends PanacheMongoEntity {

    private String accountNumber;
    private String iban;

    @Builder
    public BankAccount(String accountNumber, String iban) {
        this.accountNumber = accountNumber;
        this.iban = iban;
    }

    public BankAccount() {
        super();
    }

    public static BankAccount findByIban(String iban){
        return find("iban", iban).firstResult();
    }

}
