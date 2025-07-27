package org.colossus.resource

import jakarta.inject.Inject
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.GET
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import jakarta.ws.rs.core.Response
import org.colossus.dto.TransferRequest
import org.colossus.exception.AccountException
import org.colossus.exception.AccountNotFoundException
import org.colossus.exception.InsufficientFundsException
import org.colossus.exception.InvalidTransferException
import org.colossus.service.impl.AccountServiceImpl
import org.jboss.resteasy.reactive.server.ServerExceptionMapper
import java.math.BigDecimal

@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
class AccountResource {
    @Inject
    lateinit var accountServiceImpl: AccountServiceImpl

    @POST
    @Path("/transfer")
    fun transfer(request: TransferRequest) = accountServiceImpl.transfer(request)

    @GET
    @Path("/balance/{userId}")
    fun getBalance(@PathParam("userId") userId: String): BigDecimal = accountServiceImpl.getBalance(userId)

    @ServerExceptionMapper
    fun handleAccountException(e: AccountException): Response {
        val status = when(e) {
            is AccountNotFoundException -> Response.Status.NOT_FOUND
            is InsufficientFundsException -> Response.Status.BAD_REQUEST
            is InvalidTransferException -> Response.Status.BAD_REQUEST
            else -> Response.Status.INTERNAL_SERVER_ERROR
        }
        return Response.status(status).entity(e.message).build()
    }
}
