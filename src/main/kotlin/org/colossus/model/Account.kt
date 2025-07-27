package org.colossus.model

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "accounts")
class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(name = "user_id", unique = true)
    var userId: String? = null

    @Column(precision = 19, scale = 2)
    var balance: BigDecimal = BigDecimal.ZERO
}