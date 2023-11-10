package com.demo.core.web.config.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.server.SecurityWebFilterChain
import reactor.core.publisher.Mono

@Configuration
@EnableWebFluxSecurity
@Order(Integer.MIN_VALUE)
class SecurityConfigCore {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun securityWebFilterChainCore(httpSecurity: ServerHttpSecurity): SecurityWebFilterChain {
        // web config
        httpSecurity
            .httpBasic().disable()
            .formLogin().disable()
            .cors().disable()
            .csrf().disable()
        // auth config
        httpSecurity
            .authorizeExchange()
            .pathMatchers(
                "/swagger-resources/**",
                "/swagger-ui/**",
                "/v2/api-docs/**",
                "/webjars/**",
            ).permitAll()
        // exception handling
        httpSecurity
            .exceptionHandling()
            .accessDeniedHandler { _, _ -> Mono.error(AccessDeniedException("")) }
            .authenticationEntryPoint { _, _ -> Mono.error(AuthenticationCredentialsNotFoundException("")) }
        return httpSecurity.build();
    }


}