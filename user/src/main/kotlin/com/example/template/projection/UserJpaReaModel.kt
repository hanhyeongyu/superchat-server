package com.example.template.projection

import com.example.template.model.User
import com.example.template.repository.UserRepository
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
internal class UserJpaReaModel(
    private val memberRepository: UserRepository
): UserReadModel {

    override fun user(email: String): User? {
        return memberRepository.findByEmail(email)
            .getOrNull()
    }
}