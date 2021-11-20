package com.psd2.client.rest.client;

import com.psd2.client.database.data.Payment;
import com.psd2.client.request.data.PaymentData;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RegisterRestClient(configKey = "bank-service-api")
@Path("/payments/domestic")
public interface PaymentService {

    @POST
    Payment pay(@HeaderParam("clientId") String id, PaymentData paymentData);

    @GET
    @Path("{paymentId}")
    Payment getPayment(@HeaderParam("clientId") String id,@PathParam("paymentId") String paymentId);

    @GET
    @Path("{paymentId}/status")
    Payment getStatus(@HeaderParam("clientId") String id,@PathParam("paymentId") String paymentId);

    @DELETE
    @Path("{paymentId}")
    String deletePayment(@HeaderParam("clientId") String id, @PathParam("paymentId") String paymentId);
}
