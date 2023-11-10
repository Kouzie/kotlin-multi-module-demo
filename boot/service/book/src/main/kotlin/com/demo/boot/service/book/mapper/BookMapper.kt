package com.demo.boot.service.book.mapper

import com.demo.boot.service.book.adaptor.out.persistence.BookEntity
import com.demo.data.book.domain.BookDomain
import com.demo.data.book.dto.BookDto
import org.springframework.stereotype.Component

@Component
class BookMapper {
    fun toEntity(domain: BookDomain): BookEntity {
        return BookEntity(
            bookId = domain.bookId,
            title = domain.title,
            authorId = domain.authorId,
            desc = domain.desc,
        )
    }

    fun toDomain(entity: BookEntity): BookDomain {
        return BookDomain(
            bookId = entity.bookId,
            title = entity.title,
            authorId = entity.authorId,
            desc = entity.desc,
        )
    }

    fun toDto(domain: BookDomain): BookDto {
        return BookDto(
            id = domain.bookId,
            title = domain.title,
            authorId = domain.authorId,
            desc = domain.desc,
        )
    }
}