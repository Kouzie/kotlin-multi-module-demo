package com.demo.boo.service.customer.adaptor.out.persistence

import org.springframework.data.r2dbc.repository.R2dbcRepository

interface CustomerRepository : R2dbcRepository<CustomerEntity, Long> {
}