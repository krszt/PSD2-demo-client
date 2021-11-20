package com.psd2.client.database.data;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
public class Transaction extends PanacheMongoEntity {

    private String status;
    private String endToEndId;
    private String mandateId;
    private LocalDate bookingDate;
    private LocalDate valueDate;
    private String creditorName;
    private String ultimateCreditor;
    private String debtorName;
    private String ultimateDebtor;
    private String remittanceInformationUnstructured;
    private String remittanceInformationStructured;
    private String purposeCode;
    private String bankTransactionCode;
    private Amount transactionAmount;
    private BankAccount debtorAccount;
    private BankAccount creditorAccount;


    @Builder
    public Transaction(String status, String endToEndId, String mandateId, LocalDate bookingDate, LocalDate valueDate, String creditorName, String ultimateCreditor, String debtorName, String ultimateDebtor, String remittanceInformationUnstructured, String remittanceInformationStructured, String purposeCode, String bankTransactionCode, Amount transactionAmount, BankAccount debtorAccount, BankAccount creditorAccount) {
        this.status = status;
        this.endToEndId = endToEndId;
        this.mandateId = mandateId;
        this.bookingDate = bookingDate;
        this.valueDate = valueDate;
        this.creditorName = creditorName;
        this.ultimateCreditor = ultimateCreditor;
        this.debtorName = debtorName;
        this.ultimateDebtor = ultimateDebtor;
        this.remittanceInformationUnstructured = remittanceInformationUnstructured;
        this.remittanceInformationStructured = remittanceInformationStructured;
        this.purposeCode = purposeCode;
        this.bankTransactionCode = bankTransactionCode;
        this.transactionAmount = transactionAmount;
        this.debtorAccount = debtorAccount;
        this.creditorAccount = creditorAccount;
    }

    public Transaction() {
        super();
    }
}
