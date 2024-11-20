package com.example.template

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "spring.datasource.redis.cluster")
class ClusterConfigurationProperties(
    val nodes: List<String>
)