package com.demo.boo.service.customer.adaptor.out.persistence

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDate
import java.time.LocalDateTime

@Table("customer_entity")
class CustomerEntity(
    @Id
    var customerId: Long? = null,
    val username: String,
    var password: String,
    var nickname: String,
    var name: String,
    var status: String,
    var birth: LocalDateTime,
    var created: LocalDateTime,
    var updated: LocalDateTime,
) {
}