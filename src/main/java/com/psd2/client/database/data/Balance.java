package com.psd2.client.database.data;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
public class Balance extends PanacheMongoEntity {

    private String balanceType;
    private LocalDate referenceDate;
    private Amount balanceAmount;

    @Builder
    public Balance(String balanceType, LocalDate referenceDate, Amount balanceAmount) {
        this.balanceType = balanceType;
        this.referenceDate = referenceDate;
        this.balanceAmount = balanceAmount;
    }

    public Balance() {
        super();
    }

    public static Balance findByCurrency(String currency){

        Amount amount = Amount.findByCurrency(currency);

        return find("balanceAmount", amount).firstResult();
    }

}
