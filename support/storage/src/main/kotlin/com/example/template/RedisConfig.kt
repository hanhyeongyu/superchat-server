package com.example.template

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisClusterConfiguration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.StringRedisTemplate


@Configuration
class RedisConfig(
    private val clusterProperties: ClusterConfigurationProperties
){


    @Bean
    fun connectionFactory(): RedisConnectionFactory {
        return LettuceConnectionFactory(
            RedisClusterConfiguration(clusterProperties.nodes)
        )
    }

    @Bean
    fun stringTemplate(connectionFactory: RedisConnectionFactory): StringRedisTemplate {
        val template = StringRedisTemplate()
        template.connectionFactory = connectionFactory
        template.setEnableTransactionSupport(true)
        return template
    }


}