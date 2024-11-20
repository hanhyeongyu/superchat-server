package com.example.template.codable

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
internal class PasswordEncodableImp(
    private val encoder: PasswordEncoder
): PasswordEncodable {

    override fun encode(password: String): String {
        return encoder.encode(password)
    }

    override fun matches(rawPassword: String, encodedPassword: String): Boolean {
        return encoder.matches(rawPassword, encodedPassword)
    }

}