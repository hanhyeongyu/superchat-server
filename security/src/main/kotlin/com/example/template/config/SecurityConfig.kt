package com.example.template.config

import com.example.template.jwt.JwtProvider
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.AccessDeniedHandler

@Configuration
@EnableWebSecurity
internal class SecurityConfig{

    @Bean
    fun securityFilterChain(
        http: HttpSecurity,
        jwtProvider: JwtProvider,
        authenticationEntryPoint: AuthenticationEntryPoint,
        accessDeniedHandler: AccessDeniedHandler,
        authenticationProvider: AuthenticationProvider,
    ): SecurityFilterChain {
        http{
            csrf {
                disable()
            }

            authenticationManager = ProviderManager(authenticationProvider)

            oauth2ResourceServer {
                jwt {
                    this.jwtDecoder = jwtProvider
                }
                this.authenticationEntryPoint = authenticationEntryPoint
                this.accessDeniedHandler = accessDeniedHandler
            }
            sessionManagement {
                this.sessionCreationPolicy = SessionCreationPolicy.STATELESS
            }
            authorizeHttpRequests {
                authorize("/member/signup", permitAll)
                authorize("/member/issue/**", permitAll)
                authorize("/member/**", hasRole("MEMBER"))

                authorize("/ws/**", permitAll)

                authorize(anyRequest, authenticated)
            }
            formLogin { disable() }
            logout { disable() }
            httpBasic { disable() }
        }

        return http.build()
    }


    @Bean
    fun jwtAuthenticationProvider(jwtDecoder: JwtDecoder): AuthenticationProvider {
        val authoritiesConverter = JwtGrantedAuthoritiesConverter().apply {
            setAuthorityPrefix("")
            setAuthoritiesClaimName("roles")
            setAuthoritiesClaimDelimiter(",")
        }

        val authenticationConverter = JwtAuthenticationConverter().apply {
            setJwtGrantedAuthoritiesConverter(authoritiesConverter)
        }

        return JwtAuthenticationProvider(jwtDecoder).apply {
            setJwtAuthenticationConverter(authenticationConverter)

        }
    }

    @Bean
    fun passwordEncoder(
        @Value("\${security.password.encoder.secret}")
        passwordEncoderSecret: String
    ): PasswordEncoder {
        return Pbkdf2PasswordEncoder(
            passwordEncoderSecret,
            128,
            100,
            Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA256
        )
    }



}