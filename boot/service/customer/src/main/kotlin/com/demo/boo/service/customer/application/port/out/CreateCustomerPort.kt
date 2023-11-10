package com.demo.boo.service.customer.application.port.out

import com.demo.data.customer.domain.CustomerDomain
import reactor.core.publisher.Mono

interface CreateCustomerPort {
    fun create(domain: CustomerDomain): Mono<CustomerDomain>
}
