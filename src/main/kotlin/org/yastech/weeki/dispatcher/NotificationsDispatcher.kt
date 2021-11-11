package org.yastech.weeki.dispatcher

import org.springframework.messaging.simp.SimpMessageHeaderAccessor
import org.springframework.messaging.simp.SimpMessageType
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service
import org.yastech.weeki.model.Notification


@Service
class NotificationsDispatcher(private val template: SimpMessagingTemplate)
{

    // Send Notification
    fun send(id: String, notification: Notification)
    {
        val headerAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE)

        headerAccessor.sessionId = id

        headerAccessor.setLeaveMutable(true)

        template.convertAndSendToUser(id,"/notifications/get", notification, headerAccessor.messageHeaders)
    }
}