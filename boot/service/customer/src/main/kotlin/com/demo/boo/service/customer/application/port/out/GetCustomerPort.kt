package com.demo.boo.service.customer.application.port.out

import com.demo.data.customer.domain.CustomerDomain
import reactor.core.publisher.Mono

interface GetCustomerPort {
    fun getCustomerById(customerId: Long): Mono<CustomerDomain>
}
