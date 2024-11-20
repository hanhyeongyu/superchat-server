package com.example.template.application

import com.example.template.application.command.Signup
import com.example.template.codable.PasswordEncodable
import com.example.template.model.Email
import com.example.template.model.EncodedPassword
import com.example.template.model.User
import com.example.template.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserApplication(
    private val userRepository: UserRepository,
    private val passwordEncodable: PasswordEncodable,
){

    fun handle(command: Signup) {
        val email =  Email(command.email)
        val encodedPassword = EncodedPassword(passwordEncodable.encode(command.password))

        val user = User(
            email =  email,
            encodedPassword = encodedPassword
        )
        userRepository.save(user)
    }
}