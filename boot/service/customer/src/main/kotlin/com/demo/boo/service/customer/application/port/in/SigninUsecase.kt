package com.demo.boo.service.customer.application.port.`in`

import com.demo.data.customer.domain.CustomerDomain
import reactor.core.publisher.Mono

interface SigninUsecase {
    // hexgonal 양방향 매핑, app in ~ adaptor in 까지 domain 객체 사용
    fun signin(customerDomain: CustomerDomain): Mono<CustomerDomain>
}
