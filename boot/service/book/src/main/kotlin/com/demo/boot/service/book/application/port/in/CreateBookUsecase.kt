package com.demo.boot.service.book.application.port.`in`

import com.demo.data.book.domain.BookDomain
import reactor.core.publisher.Mono

interface CreateBookUsecase {
    fun create(domain: BookDomain): Mono<BookDomain>
}