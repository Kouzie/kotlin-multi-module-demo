package com.demo.boo.service.customer.adaptor.`in`.web

import com.demo.boo.service.customer.adaptor.`in`.web.dto.CreateCustomerRequest
import com.demo.boo.service.customer.mapper.CustomerMapper
import com.demo.boo.service.customer.application.port.`in`.SigninUsecase
import com.demo.boo.service.customer.application.port.`in`.WithDrawalUsecase
import com.demo.data.customer.dto.CustomerDto
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class CustomerController(
    val signinUsecase: SigninUsecase,
    val mapper: CustomerMapper,
) {

    @PostMapping("/customer/signin")
    fun signin(@RequestBody request: CreateCustomerRequest): Mono<CustomerDto> {
        return signinUsecase.signin(mapper.requestToDomain(request))
            .map { domain -> mapper.domainToDto(domain) }
    }

    @DeleteMapping
    fun withdrawal() {
    }
}