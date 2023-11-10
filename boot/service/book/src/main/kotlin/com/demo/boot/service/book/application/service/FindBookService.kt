package com.demo.boot.service.book.application.service

import com.demo.boot.service.book.adaptor.`in`.web.dto.FindBookRequest
import com.demo.boot.service.book.adaptor.`in`.web.dto.FindBookResult
import com.demo.boot.service.book.application.port.`in`.FindBookUsecase
import com.demo.boot.service.book.application.port.out.FindBookPort
import com.demo.data.book.domain.BookDomain
import org.springframework.stereotype.Service

@Service
class FindBookService(
    val findBookPort: FindBookPort
) : FindBookUsecase {

    override fun findBook(request: FindBookRequest): FindBookResult {
        findBookPort()
    }
}