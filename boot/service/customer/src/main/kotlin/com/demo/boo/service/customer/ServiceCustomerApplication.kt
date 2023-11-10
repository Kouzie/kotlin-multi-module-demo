package com.demo.boo.service.customer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan


@ComponentScan(basePackages = ["com.demo"])
@SpringBootApplication
class ServiceCustomerApplication

fun main(args: Array<String>) {
    runApplication<ServiceCustomerApplication>(*args)
}
