package com.demo.data.book.dto

data class BookDto(
    var id: String? = null,
    var title: String,
    var authorId: String,
    var desc: String,
)