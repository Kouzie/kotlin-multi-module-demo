package com.beyless.edge.core.filter

import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
class CorsFilter : WebFilter {

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {
        exchange.response.headers.set("Access-Control-Max-Age", "3600")
        exchange.response.headers.set("Access-Control-Allow-Methods", "*")
        exchange.response.headers.set("Access-Control-Allow-Origin", "*")
        exchange.response.headers.set(
            "Access-Control-Allow-Headers",
            "Origin, X-Requested-With, Content-Type, Accept, Authorization"
        );
        return chain.filter(exchange);
    }
}