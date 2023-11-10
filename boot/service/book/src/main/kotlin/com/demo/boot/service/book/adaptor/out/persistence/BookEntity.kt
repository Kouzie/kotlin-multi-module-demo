package com.demo.boot.service.book.adaptor.out.persistence

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field


@Document(indexName = "book")
class BookEntity(
    @Id
    var bookId: String? = null,
    val title: String,
    val authorId: String,
    @Field("desc")
    val desc: String,
)