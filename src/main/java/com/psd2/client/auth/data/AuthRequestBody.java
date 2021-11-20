package com.psd2.client.auth.data;

import lombok.Data;

@Data
public class AuthRequestBody {

    private String username;
    private String password;
}
