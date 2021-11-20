package com.psd2.client.database.data;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Slf4j
public class Account extends PanacheMongoEntity {

    private String accountId;

    private String iban;
    private String accountNumber;
    private String currency;
    private String name;
    private String product;

    private String cashAccountType;
    private String bic;
    private List<Balance> balances;
    private List<Transaction> transactions;

    @Builder
    public Account(String accountId, String iban, String accountNumber, String currency, String name, String product, String cashAccountType, String bic, List<Balance> balances, List<Transaction> transactions) {
        this.accountId = accountId;
        this.iban = iban;
        this.accountNumber = accountNumber;
        this.currency = currency;
        this.name = name;
        this.product = product;
        this.cashAccountType = cashAccountType;
        this.bic = bic;
        this.balances = balances;
        this.transactions = transactions;
    }

    public Account() {
        super();
    }

    public static Account findByIban(String iban){
        return find("iban", iban).firstResult();
    }

    public static Account findByAccountId(String id){
        return find("accountId", id).firstResult();
    }


    public Balance findByCurrency(String currency){
        for (Balance balance : balances) {
            if (balance.getBalanceAmount().getCurrency().equals(currency)) {
                return balances.get(0);
            }
        }

        return null;
    }

    public static List<Account> listAllAccount(){
        return Account.listAll();
    }
}
