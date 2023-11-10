package com.demo.data.customer.domain

import java.time.LocalDate
import java.time.LocalDateTime

class CustomerDomain(
    val customerId: Long? = null,
    val username: String,
    var password: String,
    var nickname: String,
    var name: String,
    var status: CustomerStatus,
    var birth: LocalDate,
    var created: LocalDateTime,
    var updated: LocalDateTime,
) {
}