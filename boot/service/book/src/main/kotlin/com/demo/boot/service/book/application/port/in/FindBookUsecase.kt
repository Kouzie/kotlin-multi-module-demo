package com.demo.boot.service.book.application.port.`in`

import com.demo.boot.service.book.adaptor.`in`.web.dto.FindBookRequest
import com.demo.boot.service.book.adaptor.`in`.web.dto.FindBookResult
import com.demo.data.book.domain.BookDomain
import reactor.core.publisher.Flux

interface FindBookUsecase {
    fun findBook(request: FindBookRequest): Flux<BookDomain>
}