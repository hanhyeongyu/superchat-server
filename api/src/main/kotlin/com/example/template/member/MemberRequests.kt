package com.example.template.member

class MemberRequests {
    data class IssueTokenRequest(
        val email: String,
        val password: String
    )

    data class RefreshToken(
        val refreshToken: String
    )
}