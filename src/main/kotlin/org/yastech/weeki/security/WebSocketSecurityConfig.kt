package org.yastech.weeki.security

import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.SimpMessageType
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry

import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer

@Configuration
class WebSocketSecurityConfig : AbstractSecurityWebSocketMessageBrokerConfigurer()
{
    override fun configureInbound(messages: MessageSecurityMetadataSourceRegistry)
    {
        messages
            .simpTypeMatchers(SimpMessageType.CONNECT, SimpMessageType.UNSUBSCRIBE, SimpMessageType.DISCONNECT, SimpMessageType.HEARTBEAT).permitAll()
            .simpDestMatchers("/ss/**").permitAll()
            .simpSubscribeDestMatchers("/user/notifications/**").authenticated()
            .simpSubscribeDestMatchers("/user/message/**").authenticated()
            .simpSubscribeDestMatchers("/user/search/**").permitAll()
            .anyMessage().denyAll()
    }

    override fun sameOriginDisabled(): Boolean
    {
        return true
    }
}