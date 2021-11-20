package com.psd2.client.database.data;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class User extends PanacheMongoEntity {

    private String username;
    private String password;
    private List<Account> accounts;
    private List<Payment> payments;

    @Builder
    public User(String username, String password, List<Account> accounts, List<Payment> payments) {
        this.username = username;
        this.password = password;
        this.accounts = accounts;
        this.payments = payments;
    }

    public User() {
        super();
    }

    public static User findByUsername(String username){
        return find("username", username).firstResult();
    }
}
