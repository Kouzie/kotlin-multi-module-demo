package com.demo.boot.service.book.application.port.out

import com.demo.boot.service.book.adaptor.`in`.web.dto.FindBookRequest
import com.demo.data.book.domain.BookDomain
import reactor.core.publisher.Flux

interface FindBookPort {
    fun findBook(request: FindBookRequest): Flux<BookDomain>
}