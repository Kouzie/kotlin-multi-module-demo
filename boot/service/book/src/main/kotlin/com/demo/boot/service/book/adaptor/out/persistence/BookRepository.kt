package com.demo.boot.service.book.adaptor.out.persistence

import org.springframework.data.repository.reactive.ReactiveCrudRepository

interface BookRepository : ReactiveCrudRepository<BookEntity, String> {
}