package com.demo.boot.service.dashboard

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ServiceAdminApplication

fun main(args: Array<String>) {
    runApplication<ServiceAdminApplication>(*args)
}
