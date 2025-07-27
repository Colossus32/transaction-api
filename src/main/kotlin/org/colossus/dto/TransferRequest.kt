package org.colossus.dto

import java.math.BigDecimal

data class TransferRequest(
    val from: String,
    val to: String,
    val amount: BigDecimal
)