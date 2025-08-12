package org.colossus.service

import jakarta.enterprise.context.ApplicationScoped
import org.colossus.dto.TransactionEvent
import org.eclipse.microprofile.reactive.messaging.Channel
import org.eclipse.microprofile.reactive.messaging.Emitter

@ApplicationScoped
class TransactionProducer {

    @Channel("transactions-out")
    lateinit var emitter: Emitter<TransactionEvent>

    fun sendTransaction(event: TransactionEvent) {
        emitter.send(event)
    }
}