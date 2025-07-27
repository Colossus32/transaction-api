package org.colossus.exception

import jakarta.ws.rs.core.Response
import jakarta.ws.rs.ext.ExceptionMapper
import jakarta.ws.rs.ext.Provider

@Provider
class AccountExceptionMapper : ExceptionMapper<AccountException> {
    override fun toResponse(e: AccountException): Response {
        val status = when(e) {
            is AccountNotFoundException -> Response.Status.NOT_FOUND
            is InsufficientFundsException -> Response.Status.BAD_REQUEST
            is InvalidTransferException -> Response.Status.BAD_REQUEST
            else -> Response.Status.INTERNAL_SERVER_ERROR
        }
        return Response.status(status).entity(e.message).build()
    }
}