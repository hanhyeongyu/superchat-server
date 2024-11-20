package com.example.template

import com.zaxxer.hikari.HikariDataSource
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import com.zaxxer.hikari.HikariConfig


@Configuration
internal class DatabaseConfig {
    @Bean
    @ConfigurationProperties(prefix = "support.database")
    fun coreHikariConfig(): HikariConfig{
        return HikariConfig()
    }

    @Bean
    fun coreDataSource(@Qualifier("coreHikariConfig") config: HikariConfig): HikariDataSource {
        return HikariDataSource(config)
    }
}