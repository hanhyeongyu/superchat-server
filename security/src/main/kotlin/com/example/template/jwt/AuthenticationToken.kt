package com.example.template.jwt

import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import java.security.Principal

class AuthenticationToken(
    private val jwtAuthenticationToken: JwtAuthenticationToken
) : Principal {
    override fun getName(): String {
        val principal: Principal = jwtAuthenticationToken
        return principal.name
    }
}