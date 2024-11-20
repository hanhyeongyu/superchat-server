package com.example.template.config

import com.example.template.common.exception.ErrorCode.INVALID_ACCESS_TOKEN
import com.example.template.common.response.ResponseException
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.oauth2.core.OAuth2AuthenticationException
import org.springframework.security.oauth2.server.resource.BearerTokenError
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils

@Component
class RestAuthenticationEntryPoint(
    private val objectMapper: ObjectMapper
): AuthenticationEntryPoint {

    private val log = LoggerFactory.getLogger(javaClass)

    private var realmName: String? = null

    /**
     * Collect error details from the provided parameters and format according to RFC
     * 6750, specifically `error`, `error_description`, `error_uri`, and
     * `scope`.
     * @param request that resulted in an `AuthenticationException`
     * @param response so that the user agent can begin authentication
     * @param authException that caused the invocation
     */
    override fun commence(
        request: HttpServletRequest?,
        response: HttpServletResponse,
        authException: AuthenticationException?
    ) {

        var status = HttpStatus.UNAUTHORIZED
        val parameters: MutableMap<String, String?> = LinkedHashMap()
        if (this.realmName != null) {
            parameters["realm"] = this.realmName
        }
        if (authException is OAuth2AuthenticationException) {
            val error = authException.error
            parameters["error"] = error.errorCode
            if (StringUtils.hasText(error.description)) {
                parameters["error_description"] = error.description
            }
            if (StringUtils.hasText(error.uri)) {
                parameters["error_uri"] = error.uri
            }
            if (error is BearerTokenError) {
                if (StringUtils.hasText(error.scope)) {
                    parameters["scope"] = error.scope
                }
                status = error.httpStatus
            }
        }

        val wwwAuthenticate = computeWWWAuthenticateHeaderValue(parameters)
        response.addHeader(HttpHeaders.WWW_AUTHENTICATE, wwwAuthenticate)
        response.status = status.value()
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.status = HttpStatus.UNAUTHORIZED.value()
        response.characterEncoding = "UTF-8"


        val responseException = ResponseException(INVALID_ACCESS_TOKEN)
        val responseBody = objectMapper.writeValueAsString(responseException)
        response.writer?.write(responseBody)

        log.error("Authentication Exception", authException)
        log.error("Request Uri : {}", request?.requestURI)
    }

    /**
     * Set the default realm name to use in the bearer token error response
     * @param realmName
     */
    fun setRealmName(realmName: String?) {
        this.realmName = realmName
    }



    private fun computeWWWAuthenticateHeaderValue(parameters: Map<String, String?>): String {
        val wwwAuthenticate = StringBuilder()
        wwwAuthenticate.append("Bearer")
        if (parameters.isNotEmpty()) {
            wwwAuthenticate.append(" ")
            var i = 0
            for ((key, value) in parameters) {
                wwwAuthenticate.append(key).append("=\"").append(value).append("\"")
                if (i != parameters.size - 1) {
                    wwwAuthenticate.append(", ")
                }
                i++
            }
        }
        return wwwAuthenticate.toString()
    }

}