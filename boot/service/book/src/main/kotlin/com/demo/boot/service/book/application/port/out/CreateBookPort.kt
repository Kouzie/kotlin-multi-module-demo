package com.demo.boot.service.book.application.port.out

import com.demo.data.book.domain.BookDomain
import reactor.core.publisher.Mono

interface CreateBookPort {
    fun create(book: BookDomain): Mono<BookDomain>
}