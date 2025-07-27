package org.colossus

import io.quarkus.runtime.StartupEvent
import jakarta.enterprise.context.ApplicationScoped
import jakarta.enterprise.event.Observes
import jakarta.inject.Inject
import org.colossus.model.Account
import org.colossus.repository.AccountRepository
import java.math.BigDecimal

@ApplicationScoped
class Startup {
    @Inject
    lateinit var accountRepository: AccountRepository

    fun onStart(@Observes event: StartupEvent?) {
        if (accountRepository.findByUserId("user1") == null) {
            accountRepository.save(
                Account().apply {
                    userId = "user1"
                    balance = BigDecimal("1000.00")
                }
            )

            accountRepository.save(
                Account().apply {
                    userId = "user2"
                    balance = BigDecimal("500.00")
                }
            )
        }
    }
}