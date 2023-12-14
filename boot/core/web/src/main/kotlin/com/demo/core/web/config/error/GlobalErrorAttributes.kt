package com.demo.core.web.config.error

import org.slf4j.LoggerFactory
import org.springframework.boot.web.error.ErrorAttributeOptions
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes
import org.springframework.core.codec.DecodingException
import org.springframework.security.access.AccessDeniedException
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import java.util.HashMap

@Component
class GlobalErrorAttributes : DefaultErrorAttributes() {
    private val log = LoggerFactory.getLogger(this.javaClass)!!

    override fun getErrorAttributes(request: ServerRequest, options: ErrorAttributeOptions): MutableMap<String, Any> {
        val throwable = getError(request)
        log.error("error message:${throwable.message}")
        val map = mutableMapOf<String, Any>()
        if (throwable is AccessDeniedException || throwable is DecodingException) {
            map["code"] = 500;
            map["error"]= 500;
        }
        return super.getErrorAttributes(request, options)
    }
}
