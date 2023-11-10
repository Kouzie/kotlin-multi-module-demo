package com.demo.boot.service.book.application.service

import com.demo.boot.service.book.application.port.`in`.CreateBookUsecase
import com.demo.boot.service.book.application.port.out.CreateBookPort
import com.demo.data.book.domain.BookDomain
import org.springframework.stereotype.Service

@Service
class CreateBookService(
    val createBookPort: CreateBookPort,
) : CreateBookUsecase {
    override fun create(domain: BookDomain): BookDomain {
        val createdBook: BookDomain = createBookPort.create(domain)
        return createdBook;
    }
}