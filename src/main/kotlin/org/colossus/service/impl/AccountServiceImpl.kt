package org.colossus.service.impl

import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import org.colossus.dto.TransactionEvent
import org.colossus.dto.TransferRequest
import org.colossus.repository.AccountRepository
import org.colossus.service.AccountService
import org.colossus.service.TransactionProducer
import java.math.BigDecimal

@ApplicationScoped
class AccountServiceImpl : AccountService {

    @Inject
    lateinit var transactionProducer: TransactionProducer

    @Inject
    lateinit var accountRepository: AccountRepository

    override fun getBalance(userId: String): BigDecimal =
        accountRepository.findByUserId(userId)?.balance ?: BigDecimal.ZERO

    @Transactional
    override fun transfer(request: TransferRequest) {
        validateRequest(request)

        transactionProducer.sendTransaction(
            TransactionEvent(
                from = request.from,
                to = request.to,
                amount = request.amount
            )
        )
    }

    private fun validateRequest(request: TransferRequest) {
        // Базовые проверки
        require(request.amount > BigDecimal.ZERO) { "Amount must be positive" }
        require(request.from != request.to) { "Cannot transfer to yourself" }

        // Проверка формата ID (пример)
        require(request.from.matches(Regex("[a-zA-Z0-9_]+"))) { "Invalid sender format" }
        require(request.to.matches(Regex("[a-zA-Z0-9_]+"))) { "Invalid recipient format" }

        // Проверка максимальной суммы
        require(request.amount <= BigDecimal("1000000")) { "Amount exceeds limit" }
    }
}
