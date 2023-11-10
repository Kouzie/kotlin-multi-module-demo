package com.demo.boo.service.customer.adaptor.out.persistence

import com.demo.boo.service.customer.mapper.CustomerMapper
import com.demo.boo.service.customer.application.port.out.CreateCustomerPort
import com.demo.boo.service.customer.application.port.out.GetCustomerPort
import com.demo.boo.service.customer.application.port.out.UpdateCustomerPort
import com.demo.core.web.config.PersistenceAdaptor
import com.demo.data.customer.domain.CustomerDomain
import reactor.core.publisher.Mono

@PersistenceAdaptor
class CustomerPersistenceAdaptor(
    val repository: CustomerRepository,
    val mapper: CustomerMapper,
) : GetCustomerPort, CreateCustomerPort, UpdateCustomerPort {
    override fun create(domain: CustomerDomain): Mono<CustomerDomain> {
        var entity: CustomerEntity = mapper.mapToEntity(domain)
        return repository.save(entity)
            .map { mapper.mapToDomain(it) }
    }

    override fun getCustomerById(customerId: Long): Mono<CustomerDomain> {
        return repository.findById(customerId)
            .map { mapper.mapToDomain(it) }
    }

    override fun updateCustomer(customerDomain: CustomerDomain): Mono<CustomerDomain> {
        var entity = mapper.mapToEntity(customerDomain)
        return repository.save(entity)
            .map { mapper.mapToDomain(it) }
    }
}