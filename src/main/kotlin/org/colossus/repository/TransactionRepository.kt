package org.colossus.repository

import jakarta.enterprise.context.ApplicationScoped
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.colossus.model.Transaction

@ApplicationScoped
class TransactionRepository {
    @PersistenceContext
    lateinit var entityManager: EntityManager

    fun save(transaction: Transaction) {
        entityManager.persist(transaction)
    }

    fun deleteAll(){
        entityManager.createQuery("DELETE FROM Transaction").executeUpdate()
    }
}