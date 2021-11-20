package com.psd2.client.rest.client;

import com.psd2.client.database.data.Consent;
import com.psd2.client.request.data.ConsentData;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RegisterRestClient(configKey = "bank-service-api")
public interface ConsentService {

    @GET
    @Path("/consents/{consentId}")
    Consent getConsentById(@HeaderParam("clientId") String id, @PathParam("consentId") String consentId);

    @POST
    @Path("/consents")
    Consent getConsent(@HeaderParam("clientId") String id, ConsentData consentData);



    @GET
    @Path("/consents/{consentId}/status")
    String getConsentStatusById(@HeaderParam("clientId") String id, @PathParam("consentId") String consentId);

    @DELETE
    @Path("/consents/{consentId}")
    String deleteConsentById(@HeaderParam("clientId") String id, @PathParam("consentId") String consentId);


    @GET
    @Path("sca")
    Response sca(@QueryParam("accountId") String accountId);


}
