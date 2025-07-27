package org.colossus.service.impl

import io.quarkus.test.junit.QuarkusTest
import jakarta.inject.Inject
import jakarta.transaction.Transactional
import org.colossus.dto.TransferRequest
import org.colossus.exception.InsufficientFundsException
import org.colossus.model.Account
import org.colossus.repository.AccountRepository
import org.colossus.repository.TransactionRepository
import org.colossus.service.AccountService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal

@QuarkusTest
@Transactional
class AccountServiceImplTest {

    @Inject
    lateinit var accountService: AccountService

    @Inject
    lateinit var accountRepository: AccountRepository

    @Inject
    lateinit var transactionRepository: TransactionRepository

    @BeforeEach
    fun setup() {
        accountRepository.deleteAll()
        transactionRepository.deleteAll()

        // Инициализация тестовых данных
        accountRepository.save(Account().apply {
            userId = "user1"
            balance = BigDecimal("1000")
        })
        accountRepository.save(Account().apply {
            userId = "user2"
            balance = BigDecimal("500")
        })
    }

    @Test
    fun `transfer updates balances correctly`() {
        accountService.transfer(TransferRequest("user1", "user2", BigDecimal("100")))

        assertEquals(BigDecimal("900"), accountService.getBalance("user1"))
        assertEquals(BigDecimal("600"), accountService.getBalance("user2"))
    }

    @Test
    fun `transfer throws when insufficient funds`() {
        accountRepository.save(Account().apply {
            userId = "poorUser"
            balance = BigDecimal("50")
        })

        val exception = assertThrows<InsufficientFundsException> {
            accountService.transfer(TransferRequest("poorUser", "user2", BigDecimal("100")))
        }
        assertEquals("Insufficient funds", exception.message)
    }
}