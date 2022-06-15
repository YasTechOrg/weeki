package org.yastech.weeki.config

import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer


@Configuration
@EnableWebSocketMessageBroker
class WebSocketConfig : WebSocketMessageBrokerConfigurer
{
    override fun configureMessageBroker(registry: MessageBrokerRegistry)
    {
        registry.enableSimpleBroker("/notifications", "/search", "/message")
        registry.setApplicationDestinationPrefixes("/ss")
    }

    override fun registerStompEndpoints(registry: StompEndpointRegistry)
    {
        registry
            .addEndpoint("/wst")
            .setAllowedOrigins("http://localhost:8080", "http://192.168.1.38:8080", "https://weeki.de")
            .withSockJS()
    }
}