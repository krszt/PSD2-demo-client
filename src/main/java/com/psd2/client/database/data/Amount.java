package com.psd2.client.database.data;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class Amount extends PanacheMongoEntity {

    private String amount;
    private String currency;


    @Builder
    public Amount(String amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public Amount() {
        super();
    }

    public static Amount findByCurrency(String currency){
        return find("currency", currency).firstResult();

    }
}
