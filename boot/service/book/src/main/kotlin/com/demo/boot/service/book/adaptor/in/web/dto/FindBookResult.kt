package com.demo.boot.service.book.adaptor.`in`.web.dto

import com.demo.data.book.domain.BookDomain

data class FindBookResult(
    val books: List<BookDomain>,
    val count: Integer,
) {

}
