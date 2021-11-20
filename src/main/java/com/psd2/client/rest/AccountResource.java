package com.psd2.client.rest;

import com.psd2.client.database.data.*;
import com.psd2.client.rest.client.AccountService;
import com.psd2.client.service.ValidationService;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Slf4j
@Path("/accounts")
@Tag(name = "Account API", description = "A szamlainformaciok kezelesere letrejott osztaly")
public class AccountResource {

    @Inject
    @RestClient
    AccountService accountService;

    @Inject
    ValidationService validationService;

    @ConfigProperty(name = "client.id")
    String clientId;


    @POST
    @Operation(
            summary = "getAccount",
            description = "Az adott felhasznalohoz tartozo szamlakat kuldi vissza.")
    public Response getAccount(String username) {

        try {

            Consent consent = Consent.findByStatus();

            User user = User.findByUsername(username);

            if(user == null){
                return Response.status(Response.Status.FORBIDDEN).build();
            }
            if(consent == null){
                return Response.status(Response.Status.FORBIDDEN).build();
            }

            //String accounts = accountService.getAccount(clientId, consent.getConsentId());
            return Response.ok(user.getAccounts()).build();
        }catch (Exception e){
            log.info(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }


    @GET
    @Path("{accountId}")
    @Operation(
            summary = "getAccountById",
            description = "A kapott id-nak megfelelo szamlat keri a banktol.")
    public Response getAccountById(@PathParam("accountId") String id, @QueryParam("authCode") String authCode) {
        try {

            log.info("getAccount");

            log.info("authCode: " + authCode);

            String consentId = validationService.fullConsentCheck(id);

            if(consentId == null){
                Response.status(Response.Status.FORBIDDEN).build();
            }

            Account accountReceived = accountService.getAccountById(clientId, consentId, id, authCode);


            log.info("Account received: {}", accountReceived);

            if(accountReceived == null){
                return Response.status(Response.Status.BAD_REQUEST).build();
            }

            return Response.ok(accountReceived).build();
        }catch (Exception e){
            log.info(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

}
