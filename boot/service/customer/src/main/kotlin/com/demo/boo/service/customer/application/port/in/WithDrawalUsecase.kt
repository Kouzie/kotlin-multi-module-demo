package com.demo.boo.service.customer.application.port.`in`

import reactor.core.publisher.Mono

interface WithDrawalUsecase {
    fun withDrawal(customerId: Long): Mono<Unit>;
}
