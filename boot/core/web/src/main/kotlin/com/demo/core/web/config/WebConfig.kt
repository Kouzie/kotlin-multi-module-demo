package com.demo.core.web.config

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.http.codec.ClientCodecConfigurer
import org.springframework.http.codec.json.Jackson2JsonDecoder
import org.springframework.http.codec.json.Jackson2JsonEncoder
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Configuration
class WebConfig {
//    val dtf: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd") // 2022-08-10

    @Bean
    fun objectMapper(): ObjectMapper {
        val module = JavaTimeModule()
            .addSerializer(LocalDate::class.java, LocalDateSerializer(DateTimeFormatter.ISO_LOCAL_DATE))
            .addDeserializer(LocalDate::class.java, LocalDateDeserializer(DateTimeFormatter.ISO_LOCAL_DATE))
            .addSerializer(LocalDateTime::class.java, LocalDateTimeSerializer(DateTimeFormatter.ISO_DATE_TIME))
            .addDeserializer(LocalDateTime::class.java, LocalDateTimeDeserializer(DateTimeFormatter.ISO_DATE_TIME))
        return ObjectMapper()
            .registerModule(module)
            .registerModule(KotlinModule.Builder().build()) // noargs error in kotlin data class
            .setSerializationInclusion(JsonInclude.Include.NON_NULL)
            .setPropertyNamingStrategy(PropertyNamingStrategies.LOWER_CAMEL_CASE)
            .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false) // UnrecognizedPropertyException 처리
            .configure(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT, true) // empty array as null
    }

    /**
     * 주의사항: query parameter 에서 "/" 가 들어갈 경우 webclient 는 "/" 는 escape 문자로 인식하기 때문에
     * url encoding 을 진행하지 않는다, 또 WebClient 생성시 baseUrl 을 설정하지 않으면 uribuilder 를 통해
     * scheme host port path 함수를 모두 호출해야 하기 때문에 번거롭다. StringBuilder 를 통해 uri 를 직접생성하는 것을 권장.
     */
    @Bean
    fun webClient(objectMapper: ObjectMapper): WebClient {
        val jacksonStrategy = ExchangeStrategies.builder()
            .codecs { config ->
                config.defaultCodecs()
                    .jackson2JsonEncoder(Jackson2JsonEncoder(objectMapper, MediaType.APPLICATION_JSON))
                config.defaultCodecs()
                    .jackson2JsonDecoder(Jackson2JsonDecoder(objectMapper, MediaType.APPLICATION_JSON))
                config.defaultCodecs().maxInMemorySize(1024 * 1024 * 50)
            }.build()
        return WebClient.builder().exchangeStrategies(jacksonStrategy).build()
    }
}