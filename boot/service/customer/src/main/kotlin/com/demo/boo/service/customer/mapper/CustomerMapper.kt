package com.demo.boo.service.customer.mapper

import com.demo.boo.service.customer.adaptor.`in`.web.dto.CreateCustomerRequest
import com.demo.boo.service.customer.adaptor.out.persistence.CustomerEntity
import com.demo.data.customer.domain.CustomerDomain
import com.demo.data.customer.domain.CustomerStatus
import com.demo.data.customer.dto.CustomerDto
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Component
class CustomerMapper(
) {

    val dtf: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd")

    fun requestToDomain(request: CreateCustomerRequest): CustomerDomain {
        val now = LocalDateTime.now();
        return CustomerDomain(
            username = request.username,
            password = request.password,
            nickname = request.nickname,
            name = request.name,
            status = CustomerStatus.NON_CERTIFICATED,
            birth = LocalDate.parse(request.birth, dtf),
            created = now,
            updated = now,
        )
    }

    fun mapToDomain(entity: CustomerEntity): CustomerDomain {
        return CustomerDomain(
            customerId = entity.customerId,
            username = entity.username,
            password = entity.password,
            nickname = entity.nickname,
            name = entity.name,
            status = CustomerStatus.valueOf(entity.status),
            birth = entity.birth.toLocalDate(),
            created = entity.created,
            updated = entity.updated,
        )
    }

    fun mapToEntity(domain: CustomerDomain): CustomerEntity {
        return CustomerEntity(
            customerId = domain.customerId,
            username = domain.username,
            password = domain.password,
            nickname = domain.nickname,
            name = domain.name,
            status = domain.status.name,
            birth = domain.birth.atStartOfDay(),
            created = domain.created,
            updated = domain.updated,
        )
    }

    fun domainToDto(domain: CustomerDomain): CustomerDto {
        return CustomerDto(
            customerId = domain.customerId,
            username = domain.username,
            password = domain.password,
            nickname = domain.nickname,
            name = domain.name,
            status = domain.status,
            birth = domain.birth,
            created = domain.created,
            updated = domain.updated,
        )
    }
}