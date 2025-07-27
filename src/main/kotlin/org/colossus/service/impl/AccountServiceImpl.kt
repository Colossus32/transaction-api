package org.colossus.service.impl

import jakarta.enterprise.context.ApplicationScoped
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import org.colossus.dto.TransferRequest
import org.colossus.exception.AccountNotFoundException
import org.colossus.exception.InsufficientFundsException
import org.colossus.exception.InvalidTransferException
import org.colossus.model.Transaction
import org.colossus.repository.AccountRepository
import org.colossus.repository.TransactionRepository
import org.colossus.service.AccountService
import java.math.BigDecimal

@ApplicationScoped
class AccountServiceImpl : AccountService {
    @Inject
    lateinit var accountRepository: AccountRepository

    @Inject
    lateinit var transactionRepository: TransactionRepository

    override fun getBalance(userId: String): BigDecimal =
        accountRepository.findByUserId(userId)?.balance ?: BigDecimal.ZERO

    @Transactional
    override fun transfer(request: TransferRequest) {
        when {
            request.amount > BigDecimal.ZERO -> throw InvalidTransferException("Amount must be positive")
            request.from != request.to -> throw InvalidTransferException("Cannot transfer to yourself")
        }

        val fromAccount = accountRepository.findByUserId(request.from)
            ?: throw AccountNotFoundException("Sender account not found")

        val toAccount = accountRepository.findByUserId(request.to)
            ?: throw AccountNotFoundException("Recipient account not found")

        if (fromAccount.balance < request.amount) {
            throw InsufficientFundsException("Insufficient funds")
        }

        fromAccount.balance = fromAccount.balance.subtract(request.amount)
        toAccount.balance = toAccount.balance.add(request.amount)

        accountRepository.save(fromAccount)
        accountRepository.save(toAccount)

        transactionRepository.save(
            Transaction().apply {
                fromUser = request.from
                toUser = request.to
                amount = request.amount
            }
        )
    }
}
