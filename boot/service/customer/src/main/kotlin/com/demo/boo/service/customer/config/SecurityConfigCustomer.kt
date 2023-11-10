package com.demo.boo.service.customer.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain


@Configuration
class SecurityConfigCustomer {

    @Bean
    @Order(1)
    fun securityWebFilterChainCustomer(httpSecurity: ServerHttpSecurity): SecurityWebFilterChain {
        httpSecurity
            .httpBasic().disable()
            .formLogin().disable()
            .cors().disable()
            .csrf().disable()

        httpSecurity
            .authorizeExchange()
            .pathMatchers(
                "/customer/**",
            ).permitAll()

        return httpSecurity.build()
    }
}