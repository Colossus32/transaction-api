package org.colossus.service

import jakarta.transaction.Transactional
import org.colossus.dto.TransactionEvent
import org.colossus.model.Transaction
import org.colossus.repository.AccountRepository
import org.colossus.repository.TransactionRepository
import org.eclipse.microprofile.reactive.messaging.Incoming

class TransactionConsumer(
    private val accountRepository: AccountRepository,
    private val transactionRepository: TransactionRepository
) {

    @Incoming("transaction-in")
    @Transactional
    fun processTransaction(event: TransactionEvent) {
        runCatching {
            val fromAccount = accountRepository.findByUserId(event.from)
                ?: throw IllegalArgumentException("Sender account not found")

            val toAccount = accountRepository.findByUserId(event.to)
                ?: throw IllegalArgumentException("Recipient account not found")

            fromAccount.balance -= event.amount
            toAccount.balance += event.amount

            transactionRepository.save(
                Transaction(
                    fromUser = event.from,
                    toUser = event.to,
                    amount = event.amount
                )
            )
        }
    }
}