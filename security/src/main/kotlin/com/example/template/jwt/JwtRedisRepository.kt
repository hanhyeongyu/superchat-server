package com.example.template.jwt

import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Repository
import java.util.concurrent.TimeUnit

@Repository
internal class JwtRedisRepository(
    private val redisTemplate: StringRedisTemplate
): JwtRepository {


    override fun add(id: String, refreshToken: String, refreshExpiration: Long) {
        valueOperator().set(
            key(id),
            refreshToken,
            refreshExpiration,
            TIME_UNIT
        )
    }

    override fun token(id: String): String? {
        return valueOperator().get(key(id))
    }


    private fun valueOperator() = redisTemplate.opsForValue()

    private fun key(userId: String): String{
        return  "$TOKEN_KEY_PREFIX:${userId}"
    }

    companion object{
        private const val TOKEN_KEY_PREFIX = "refresh_token"
        private  val TIME_UNIT = TimeUnit.MILLISECONDS
    }

}