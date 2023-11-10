package com.demo.boo.service.customer.application.service

import com.demo.boo.service.customer.application.port.`in`.SigninUsecase
import com.demo.boo.service.customer.application.port.out.CreateCustomerPort
import com.demo.data.customer.domain.CustomerDomain
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class SigninService(
    private val createCustomerPort: CreateCustomerPort,
) : SigninUsecase {

    override fun signin(customerDomain: CustomerDomain): Mono<CustomerDomain> {
        return createCustomerPort.create(customerDomain)
    }
}