package com.example.template.jwt

interface JwtService {
    fun issueToken(id: String, roles: List<String>): IssueToken
    fun issueToken(refreshToken: String): IssueToken
    fun authentication(token: String): AuthenticationToken
}