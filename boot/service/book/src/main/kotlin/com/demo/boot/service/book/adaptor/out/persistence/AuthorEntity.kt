package com.demo.boot.service.book.adaptor.out.persistence

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field


@Document(indexName = "book")
class AuthorEntity(
    @Id
    var authorId: String? = null,
    val name: String,
    @Field("desc")
    val desc: String,
)