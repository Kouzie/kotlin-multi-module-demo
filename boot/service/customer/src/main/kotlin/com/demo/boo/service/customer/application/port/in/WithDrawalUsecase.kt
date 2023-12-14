package com.demo.boo.service.customer.application.port.`in`

import com.demo.data.customer.domain.CustomerDomain
import reactor.core.publisher.Mono

interface WithDrawalUsecase {
    fun withDrawal(customerId: Long): Mono<CustomerDomain>
}
