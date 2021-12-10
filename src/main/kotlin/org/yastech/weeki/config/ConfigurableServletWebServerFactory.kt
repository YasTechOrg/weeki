package org.yastech.weeki.config

import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory
import org.springframework.context.annotation.Bean

@Bean
fun webServerFactory(): ConfigurableServletWebServerFactory
{
    val factory = JettyServletWebServerFactory()
    factory.port = 8080
    factory.contextPath = "/"
    return factory
}