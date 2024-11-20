package com.example.template.jwt


interface JwtRepository{
    fun add(id: String, refreshToken : String, refreshExpiration: Long)
    fun token(id: String): String?
}

