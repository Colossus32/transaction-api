package org.colossus.dto

import java.math.BigDecimal
import java.util.UUID

data class TransactionEvent(
    val eventId: String = UUID.randomUUID().toString(),
    val from: String,
    val to: String,
    val amount: BigDecimal,
    val status: TransactionStatus = TransactionStatus.PENDING
)

enum class TransactionStatus {
    PENDING, COMPLETED, FAILED
}
