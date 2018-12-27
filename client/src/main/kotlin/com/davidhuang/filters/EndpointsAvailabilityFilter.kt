package com.davidhuang.filters

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.http.server.ServletServerHttpRequest
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.servlet.NoHandlerFoundException
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class EndpointsAvailabilityFilter @Autowired constructor(
    private val env: Environment
): OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val requestURI = request.requestURI
        val requestMethod = request.method
        val property = "${requestURI.substring(1).replace("/", ".")}." +
                "${requestMethod.toLowerCase()}.enabled"
        val enabled = env.getProperty(property, "true")
        if (!enabled.toBoolean()) {
            throw NoHandlerFoundException(requestMethod, requestURI, ServletServerHttpRequest(request).headers)
        }
        filterChain.doFilter(request, response)
    }
}