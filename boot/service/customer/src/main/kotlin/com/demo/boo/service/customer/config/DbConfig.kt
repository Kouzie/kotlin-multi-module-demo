package com.demo.boo.service.customer.config

import io.r2dbc.h2.H2ConnectionConfiguration
import io.r2dbc.h2.H2ConnectionFactory
import io.r2dbc.h2.H2ConnectionOption
import io.r2dbc.spi.ConnectionFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

import io.r2dbc.spi.ConnectionFactoryOptions
import org.slf4j.LoggerFactory
import org.springframework.core.io.ClassPathResource

import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator
import io.r2dbc.spi.ConnectionFactories
import org.h2.tools.Server
import org.springframework.context.event.ContextClosedEvent

import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import java.sql.SQLException


@Configuration
class DbConfig {
    private val log = LoggerFactory.getLogger(this.javaClass)!!

    /**
     * <https://github.com/r2dbc/r2dbc-h2>
     * */
    @Bean
    fun connectionFactory(): ConnectionFactory {
        var config = H2ConnectionConfiguration.builder()
            .inMemory("testdb")
            .property(H2ConnectionOption.DB_CLOSE_DELAY, "-1")
//            .property("DATABASE_TO_UPPER", "false")
            .username("sa")
            .build()
        log.info("connectionInfo: $config")
        return H2ConnectionFactory(config)
    }

    @Bean
    fun h2DbInitializer(): ConnectionFactoryInitializer? {
        val initializer = ConnectionFactoryInitializer()
        val resourceDatabasePopulator = ResourceDatabasePopulator()
        resourceDatabasePopulator.addScript(ClassPathResource("create.sql"))
        initializer.setConnectionFactory(connectionFactory())
        initializer.setDatabasePopulator(resourceDatabasePopulator)
        return initializer
    }

    private lateinit var webServer: Server;

    @EventListener(ContextRefreshedEvent::class)
    @Throws(SQLException::class)
    fun start() {
        log.info("starting h2 console at port 8078")
        webServer = Server.createWebServer("-webPort", "8078", "-tcpAllowOthers").start()
    }

    @EventListener(ContextClosedEvent::class)
    fun stop() {
        log.info("stopping h2 console at port 8078")
        webServer.stop()
    }
}