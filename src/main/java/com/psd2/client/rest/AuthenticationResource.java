package com.psd2.client.rest;

import com.psd2.client.auth.data.AuthRequestBody;
import com.psd2.client.auth.data.AuthRequestResponse;
import com.psd2.client.auth.data.RegistrationResponse;
import com.psd2.client.service.AuthenticationService;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Slf4j
@Tag(name = "Authentication API", description = "Az authentikacioert felelos osztaly")
public class AuthenticationResource {

    @Inject
    AuthenticationService authenticationService;

    @POST
    @Path("login")
    @Operation(
            summary = "login",
            description = "Belepteti a felhasznalot, ha megfelelo adatokat adott meg.")
    public Response login(AuthRequestBody body) {
        log.info("Login data: {}", body);
        try{
            boolean isSuccessful = authenticationService.login(body);

            AuthRequestResponse authRequestResponse = AuthRequestResponse.builder()
                    .isSuccessfull(isSuccessful)
                    .username(body.getUsername())
                    .build();

            return Response.ok(authRequestResponse).build();
        }catch (Exception e){
            log.info(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @POST
    @Path("register")
    @Operation(
            summary = "register",
            description = "Regisztralja a felhasznalot a megadott adatokkal.")
    public Response register(AuthRequestBody body) {
        log.info("Register data: {}", body);

        try{
            String isSuccessful = authenticationService.register(body);
            RegistrationResponse registrationResponse;
            if(isSuccessful.equals("OK")){
                registrationResponse = RegistrationResponse.builder()
                        .isSuccessfull(true)
                        .userCheck(isSuccessful)
                        .username(body.getUsername())
                        .build();
                log.info("Registration successful with: {}", body);
            }else if(isSuccessful.equals("RESERVED")){
                registrationResponse = RegistrationResponse.builder()
                        .isSuccessfull(false)
                        .userCheck(isSuccessful)
                        .username(body.getUsername())
                        .build();
                log.info("Username: {} is already in use", body.getUsername());
            }else {
                registrationResponse = RegistrationResponse.builder()
                        .isSuccessfull(false)
                        .userCheck(isSuccessful)
                        .username(body.getUsername())
                        .build();
                log.info("Something bad happened in authenticationService.register");
            }

            return Response.ok(registrationResponse).build();
        }catch (Exception e){
            log.info(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
