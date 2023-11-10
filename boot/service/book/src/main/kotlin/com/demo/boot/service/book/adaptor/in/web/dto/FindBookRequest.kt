package com.demo.boot.service.book.adaptor.`in`.web.dto

import com.demo.core.web.config.ValidEnum
import com.demo.data.book.domain.BookCategory

data class FindBookRequest(
    @ValidEnum(enumClass = BookCategory::class)
    val category: BookCategory,
    val title: String,
) {
}
