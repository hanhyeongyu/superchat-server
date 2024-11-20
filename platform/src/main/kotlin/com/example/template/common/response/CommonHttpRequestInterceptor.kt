package com.example.template.common.response

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.MDC
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import org.springframework.web.servlet.HandlerInterceptor
import java.util.*

@Component
class CommonHttpRequestInterceptor : HandlerInterceptor {
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        var requestEventId: String = request.getHeader(HEADER_REQUEST_UUID_KEY)
        if (StringUtils.hasText(requestEventId)) {
            requestEventId = UUID.randomUUID().toString()
        }

        MDC.put(HEADER_REQUEST_UUID_KEY, requestEventId)
        return true
    }

    companion object {
        const val HEADER_REQUEST_UUID_KEY: String = "x-request-id"
    }
}
