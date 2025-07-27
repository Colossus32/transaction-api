package org.colossus.repository

import jakarta.enterprise.context.ApplicationScoped
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import jakarta.transaction.Transactional
import org.colossus.model.Account

@ApplicationScoped
class AccountRepository {
    @PersistenceContext
    lateinit var entityManager: EntityManager

    fun findByUserId(userId: String): Account? {
        return entityManager.createQuery(
            "select a from Account a where a.userId = :userId",
            Account::class.java
        ).setParameter("userId", userId)
            .resultList
            .firstOrNull()
    }

    @Transactional
    fun save(account: Account) {
        with(account) {
            when (id == null) {
                true -> { entityManager.persist(this) }
                else -> { entityManager.merge(this) }
            }
        }
    }

    fun deleteAll() {
        entityManager.createQuery("DELETE FROM Account").executeUpdate()
    }
}