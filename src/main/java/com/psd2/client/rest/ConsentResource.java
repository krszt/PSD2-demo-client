package com.psd2.client.rest;

import com.psd2.client.database.data.Account;
import com.psd2.client.database.data.Consent;
import com.psd2.client.database.data.User;
import com.psd2.client.request.data.ConsentData;
import com.psd2.client.rest.client.ConsentService;
import com.psd2.client.service.TrippleDes;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Slf4j
@Path("/consents")
@Tag(name = "Consent API", description = "A consent-ek kezelesere letrejott osztaly")
public class ConsentResource {

    @Inject
    @RestClient
    ConsentService consentService;

    @Inject
    TrippleDes trippleDes;


    @ConfigProperty(name = "client.id")
    String id;


    @POST
    @Operation(
            summary = "getConsent",
            description = "Ker a banktol egy consentet az adott szamlahoz.")
    public Response getConsent(@QueryParam("iban") String iban, @QueryParam("username") String username, @QueryParam("pinCode") String pinCode) {
        try {
            log.info("getConsent-ben");
            log.info("IBAN: {}", iban);
            log.info("Username: {}", username);
            Consent consent = Consent.findByAccountAndStatus(iban);
            if(consent != null){
                log.info("consent létezik már: {}", consent);

                getConsentById(consent.getConsentId());
                consent = Consent.findByConsentId(consent.getConsentId());


                if(consent.getStatus().equals("invalid")){
                    return Response.status(Response.Status.UNAUTHORIZED).build();
                }

                Response auth = consentService.sca(Account.findByIban(iban).getAccountId());
                if(auth.getStatus() != 200){
                    log.info("status: {}", auth.getStatus());
                    return Response.status(Response.Status.FORBIDDEN).build();
                }

                log.info("Response: {}", consent);
                return Response.ok(consent).build();
            }else {

                Account account = Account.findByIban(iban);
                if(account == null){
                    account = Account.builder()
                            .iban(iban)
                            .build();

                    account.persist();
                }

                String encrypted = trippleDes.encrypt(pinCode);


                ConsentData consentData = ConsentData.builder()
                        .iban(iban)
                        .frequencyPerDay("5")
                        .recurringIndicator(false)
                        .validUntil(LocalDate.now().plusMonths(1))
                        .account(account)
                        .pinCode(encrypted)
                        .build();

                log.info("consent data létrejött");
                Consent consentReceived = consentService.getConsent(id, consentData);

                log.info("ez meg megy");
                log.info("Valami {}", consentReceived.getStatus().equals("invalid"));
                if(consentReceived.getStatus().equals("invalid")){
                    return Response.status(Response.Status.UNAUTHORIZED).build();
                }

                log.info("consentReceived: {}", consentReceived);
                account.setAccountId(consentReceived.getAccount().getAccountId());

                account.persistOrUpdate();
                log.info("account persisted: {}", account);

                consentReceived.persist();
                log.info("consent persisted: {}", consentReceived);

                User user = User.findByUsername(username);

                log.info("User: {}", user);
                if(user.getAccounts() == null){
                    user.setAccounts(Arrays.asList(account));
                }else{
                    List<Account> accounts = user.getAccounts();
                    accounts.add(account);

                    user.setAccounts(accounts);
                }

                user.persistOrUpdate();


                return Response.ok(consentReceived).build();
            }
        }catch (Exception e){
            log.info(e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GET
    @Path("{consentId}")
    @Operation(
            summary = "getConsentById",
            description = "A kapott id-nak megfelelo consentet keri a banktol.")
    public Response getConsentById(@PathParam("consentId") String id) {
        try {
            Consent cons = Consent.findByConsentId(id);

            List<Consent> consents = Arrays.asList(cons);

            for (Consent consent : consents) {
                Consent consentReceived = consentService.getConsentById(id, consent.getConsentId());

                log.info("consentReceived: {}", consentReceived);
                consentReceived.persistOrUpdate();

                if(consentReceived.getStatus().equals("invalid")){
                    return Response.status(Response.Status.FORBIDDEN).build();
                }
            }

            return Response.ok().build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }



    @GET
    @Path("consents/{example}")
    @Operation(
            summary = "Example")
    public Response getExample(@PathParam("example") String ex) {
        try {

            //Business logic

            return Response.ok().build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}

