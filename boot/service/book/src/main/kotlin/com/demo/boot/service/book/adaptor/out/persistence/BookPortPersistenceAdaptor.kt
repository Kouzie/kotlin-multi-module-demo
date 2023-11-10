package com.demo.boot.service.book.adaptor.out.persistence

import com.demo.boot.service.book.adaptor.`in`.web.dto.FindBookRequest
import com.demo.boot.service.book.application.port.out.CreateBookPort
import com.demo.boot.service.book.application.port.out.FindBookPort
import com.demo.boot.service.book.mapper.BookMapper
import com.demo.core.web.config.PersistenceAdaptor
import com.demo.data.book.domain.BookDomain
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@PersistenceAdaptor
class BookPortPersistenceAdaptor(
    val mapper: BookMapper,
    val repository: BookRepository,
) : CreateBookPort, FindBookPort {
    override fun create(domain: BookDomain): Mono<BookDomain> {
        val book: BookEntity = mapper.toEntity(domain)
        return repository.save(book)
            .map { it -> mapper.toDomain(it) }
    }

    override fun findBook(request: FindBookRequest): Flux<BookDomain> {
        repository(request.)
    }

}