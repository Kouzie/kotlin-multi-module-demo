package com.demo.boo.service.customer.application.port.out

import com.demo.data.customer.domain.CustomerDomain
import reactor.core.publisher.Mono

interface UpdateCustomerPort {
    fun updateCustomer(customerDomain: CustomerDomain): Mono<CustomerDomain>
}
