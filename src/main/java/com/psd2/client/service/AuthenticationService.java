package com.psd2.client.service;

import com.psd2.client.auth.data.AuthRequestBody;
import com.psd2.client.database.data.User;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
@Slf4j
public class AuthenticationService {

    @Inject
    TrippleDes trippleDes;

    public boolean login(AuthRequestBody body) {
        try {
            User user = User.findByUsername(body.getUsername());

            if (user != null) {
                return trippleDes.decrypt(user.getPassword()).equals(body.getPassword());
            } else {
                return false;
            }

        } catch (Exception e) {
            log.info(e.getMessage());
            return false;
        }
    }

    public String register(AuthRequestBody body) {
        try {
            User user = User.findByUsername(body.getUsername());

            String target=body.getPassword();
            String encrypted=trippleDes.encrypt(target);

            if (user == null) {
                user = User.builder()
                        .username(body.getUsername())
                        .password(encrypted)
                        .build();
                user.persist();
                return "OK";
            }else{
                return "RESERVED";
            }

        } catch (Exception e) {
            log.info(e.getMessage());
            return "ERROR";
        }
    }
}
