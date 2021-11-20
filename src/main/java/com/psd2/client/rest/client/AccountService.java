package com.psd2.client.rest.client;

import com.psd2.client.database.data.Account;
import com.psd2.client.database.data.Balance;
import com.psd2.client.database.data.Transaction;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RegisterRestClient(configKey = "bank-service-api")
@Path("/accounts")
public interface AccountService {

    /*@POST
    String getAccount(@HeaderParam("clientId") String id,@HeaderParam("consentId") String consentId);*/

    @GET
    @Path("{accountId}")
    Account getAccountById(@HeaderParam("clientId") String id,@HeaderParam("consentId") String consentId,@PathParam("accountId") String accountId,@QueryParam("authCode") String authCode);

    @GET
    @Path("{accountId}/balances")
    List<Balance> getBalancesById(@HeaderParam("clientId") String id,@HeaderParam("consentId") String consentId,@PathParam("accountId") String accountId);

    @GET
    @Path("{accountId}/transactions")
    List<Transaction> getTransactionsById(@HeaderParam("clientId") String id,@HeaderParam("consentId") String consentId,@PathParam("accountId") String accountId);


}
