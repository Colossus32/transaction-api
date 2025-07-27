package org.colossus.model

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "transactions")
class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(name = "from_user")
    var fromUser: String? = null

    @Column(name = "to_user")
    var toUser: String? = null

    @Column(precision = 19, scale = 2)
    var amount: BigDecimal = BigDecimal.ZERO

    @Column(name = "created_at")
    var createdAt: LocalDateTime = LocalDateTime.now()
}