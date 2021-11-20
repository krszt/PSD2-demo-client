package com.psd2.client.auth.data;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthRequestResponse {
    private boolean isSuccessfull;
    private String username;
}
