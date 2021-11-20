package com.psd2.client.auth.data;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegistrationResponse {
    private boolean isSuccessfull;
    private String userCheck;
    private String username;
}
