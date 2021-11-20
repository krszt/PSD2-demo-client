package com.psd2.client.database.data;

import io.quarkus.mongodb.panache.PanacheMongoEntityBase;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bson.codecs.pojo.annotations.BsonId;

import java.time.LocalDate;


@EqualsAndHashCode(callSuper = true)
@Data
public class Payment extends PanacheMongoEntityBase {


    @BsonId
    private String paymentId;

    private BankAccount debtor;
    private String ultimateDebtor;
    private String currency;
    private String amount;

    private BankAccount creditor;
    private String ultimateCreditor;

    private LocalDate requestedDate;

    @Builder
    public Payment(BankAccount debtor, String paymentId, String ultimateDebtor, String currency, String amount, BankAccount creditor, String ultimateCreditor, LocalDate requestedDate) {
        this.debtor = debtor;
        this.ultimateDebtor = ultimateDebtor;
        this.currency = currency;
        this.amount = amount;
        this.creditor = creditor;
        this.ultimateCreditor = ultimateCreditor;
        this.requestedDate = requestedDate;
        this.paymentId = paymentId;
    }

    public Payment() {
        super();
    }
}