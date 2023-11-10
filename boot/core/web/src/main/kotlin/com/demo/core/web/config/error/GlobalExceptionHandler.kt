package com.demo.core.web.config.error

import org.springframework.boot.autoconfigure.web.WebProperties
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler
import org.springframework.boot.web.reactive.error.ErrorAttributes
import org.springframework.context.ApplicationContext
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse

@Order(-1)
@Component
class GlobalExceptionHandler(
    errorAttributes: GlobalErrorAttributes,
    resources: WebProperties.Resources,
    applicationContext: ApplicationContext?
) : AbstractErrorWebExceptionHandler(errorAttributes, resources, applicationContext) {
    override fun getRoutingFunction(errorAttributes: ErrorAttributes?): RouterFunction<ServerResponse> {
        TODO("Not yet implemented")
    }
}