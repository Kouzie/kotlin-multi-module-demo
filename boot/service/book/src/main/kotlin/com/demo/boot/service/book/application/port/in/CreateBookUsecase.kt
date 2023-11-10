package com.demo.boot.service.book.application.port.`in`

import com.demo.data.book.domain.BookDomain

interface CreateBookUsecase {
    fun create(domain: BookDomain): BookDomain
}