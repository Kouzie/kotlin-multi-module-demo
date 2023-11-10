package com.demo.boot.service.book.adaptor.`in`.web

import com.demo.boot.service.book.adaptor.`in`.web.dto.FindBookRequest
import com.demo.boot.service.book.adaptor.`in`.web.dto.FindBookResult
import com.demo.boot.service.book.application.port.`in`.FindBookUsecase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class FindBookController(
    val findBookUsecase: FindBookUsecase
) {
    @GetMapping
    fun getBooks(@RequestBody request: FindBookRequest): FindBookResult {
        return findBookUsecase.findBook(request)
    }
}