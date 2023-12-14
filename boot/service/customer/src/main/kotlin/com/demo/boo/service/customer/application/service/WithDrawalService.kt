package com.demo.boo.service.customer.application.service

import com.demo.boo.service.customer.application.port.`in`.SigninUsecase
import com.demo.boo.service.customer.application.port.`in`.WithDrawalUsecase
import com.demo.boo.service.customer.application.port.out.CreateCustomerPort
import com.demo.boo.service.customer.application.port.out.GetCustomerPort
import com.demo.boo.service.customer.application.port.out.UpdateCustomerPort
import com.demo.data.customer.domain.CustomerDomain
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class WithDrawalService(
    private val getCustomerPort: GetCustomerPort,
    private val updateCustomerPort: UpdateCustomerPort,
) : WithDrawalUsecase {
    override fun withDrawal(customerId: Long): Mono<CustomerDomain> {
        return getCustomerPort.getCustomerById(customerId)
            .flatMap { updateCustomerPort.updateCustomer(it) }
    }
}