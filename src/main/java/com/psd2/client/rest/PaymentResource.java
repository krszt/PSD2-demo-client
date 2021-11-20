package com.psd2.client.rest;

import com.psd2.client.database.data.Payment;
import com.psd2.client.database.data.User;
import com.psd2.client.request.data.PaymentData;
import com.psd2.client.rest.client.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Slf4j
@Path("/payments/domestic")
@Tag(name = "Payment API", description = "A fizetesek kezelesere letrejott osztaly")
public class PaymentResource {

    @Inject
    @RestClient
    PaymentService paymentService;

    @ConfigProperty(name = "client.id")
    String id;

    @POST
    @Operation(
            summary = "pay",
            description = "A kapott adatoknak megfeleloen indit egy fizetesi muveletet a banknal.")
    public Response pay(PaymentData paymentData, @QueryParam("username") String username) {

        try {
            log.info("Payment received");
            Payment paymentReceived = paymentService.pay(id,paymentData);

            paymentReceived.persist();

            log.info("paymentReceived: {}", paymentReceived);

            log.info("{}", username);
            User user = User.findByUsername(username);

            log.info("{}", user);

            if(user == null){
                log.info("user is null");
                return Response.status(Response.Status.FORBIDDEN).build();
            }
            if(user.getPayments() == null){
                log.info("user.getPayments is null");
                user.setPayments(Arrays.asList(paymentReceived));
            }else{
                log.info("user.getPayments not null");
                List<Payment> payments = user.getPayments();
                payments.add(paymentReceived);

                user.setPayments(payments);

            }

            user.persistOrUpdate();

            return Response.ok(paymentReceived).build();
        }catch (Exception e){
            log.info(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }


    @GET
    @Path("allPayment")
    @Operation(
            summary = "getPayments",
            description = "Visszakuldi a felhasznalohoz tartozo fizeteseket.")
    public Response getPayments(@QueryParam("username") String username){
        try {
            User user = User.findByUsername(username);
            return Response.ok(user.getPayments()).build();
        }catch (Exception e){
            log.info(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}