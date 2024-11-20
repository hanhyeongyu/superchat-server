package com.example.template.jwt

import io.jsonwebtoken.Jwts
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator
import org.springframework.security.oauth2.core.OAuth2TokenValidator
import org.springframework.security.oauth2.jwt.*
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*
import javax.crypto.spec.SecretKeySpec

@Component
internal class JwtProvider:
    JwtComposer,
    JwtDecoder,
    Customizer<OAuth2ResourceServerConfigurer<HttpSecurity>.JwtConfigurer> {

    private val key: Key
    private var decoder: NimbusJwtDecoder

    private val tokenType: String
    private val accessExpiration: Long
    private val refreshExpiration: Long
    private val issue: String

    @Autowired
    constructor(
        @Value("\${security.jwt.secret}") jwtSecret: String,
        @Value("\${security.jwt.issue}") issue: String,
        @Value("\${security.jwt.expiration.access}") accessExpiration: Long,
        @Value("\${security.jwt.expiration.refresh}") refreshExpiration: Long,
    ): this(
        SecretKeySpec(
            jwtSecret.toByteArray(),
            "HmacSHA256"
        ),
        issue,
        accessExpiration,
        refreshExpiration
    )

    constructor(
        key: SecretKeySpec,
        issue: String,
        accessExpiration: Long,
        refreshExpiration: Long
    ){
        this.tokenType = "Bearer"
        this.key = key
        this.issue = issue
        //this.accessExpiration  =
        this.accessExpiration  = 1000 * 60  * accessExpiration
        this.refreshExpiration = 5000 * 60  * refreshExpiration
        decoder = NimbusJwtDecoder.withSecretKey(key).build()
        decoder.setJwtValidator(tokenValidator())
    }


    override fun compose(subject: String, roles: List<String>): IssueToken {
        return IssueToken(
            tokenType,
            accessToken(subject, roles),
            accessExpiration,
            refreshToken(subject, roles),
            refreshExpiration
        )
    }

    override fun compose(refreshToken: String): IssueToken {
        val jwt = decode(refreshToken)
        val subject = jwt.subject
        val roles = (jwt.claims[JWT_ROLES] as String).split(",")
            .map { it.split("_").last() }

        return IssueToken(
            tokenType,
            accessToken(subject, roles),
            accessExpiration,
            refreshToken,
            refreshExpiration
        )
    }



    override fun decode(token: String?): Jwt {
        return decoder.decode(token)
    }

    override fun customize(jwtConfigurer: OAuth2ResourceServerConfigurer<HttpSecurity>.JwtConfigurer) {
        jwtConfigurer.decoder(this)
    }


    private fun tokenValidator(): OAuth2TokenValidator<Jwt>{
        val validators = listOf(
            JwtTimestampValidator(),
            JwtIssuerValidator(issue),
        )
        return DelegatingOAuth2TokenValidator(validators)
    }


    private fun accessToken(subject: String, roles: List<String>): String{
        return Jwts
            .builder()
            .signWith(key)
            .setIssuer(issue)
            .setIssuedAt(issueAt())
            .setExpiration(expiration(accessExpiration))
            .setSubject(subject)
            .claim(
                JWT_ROLES,
                roles.map { role -> "ROLE_$role" }
                    .joinToString(",")
            )
            .compact()
    }


    private fun refreshToken(subject: String, roles: List<String>): String{
        return Jwts
            .builder()
            .signWith(key)
            .setIssuer(issue)
            .setIssuedAt(issueAt())
            .setExpiration(expiration(refreshExpiration))
            .setSubject(subject)
            .claim(
                JWT_ROLES,
                roles.map { role -> "ROLE_$role" }
                    .joinToString(",")
            )
            .compact()
    }

    private fun issueAt(): Date {
        return Date()
    }

    private fun expiration(expiration: Long): Date {
        return Date(System.currentTimeMillis() +  expiration)
    }


    companion object{
        private const val JWT_ROLES = "roles"
    }


}


