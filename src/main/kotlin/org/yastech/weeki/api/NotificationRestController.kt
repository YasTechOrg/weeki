package org.yastech.weeki.api

import org.springframework.web.bind.annotation.*
import org.yastech.weeki.data.HexConvertor
import org.yastech.weeki.data.JWTParser
import org.yastech.weeki.dispatcher.NotificationsDispatcher
import org.yastech.weeki.model.Notification
import org.yastech.weeki.security.JWTUtils
import org.yastech.weeki.service.UserService
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/api/rest/notifications")
class NotificationRestController
(
    private var userService: UserService,
    private var jwtParser: JWTParser,
    private var jwtUtils: JWTUtils,
    private var hexConvertor: HexConvertor,
    private var notificationsDispatcher: NotificationsDispatcher
)
{
    @GetMapping("/get")
    fun getNotifications(request: HttpServletRequest): Mono<MutableList<Notification>>
    {
        return userService.get(
            jwtUtils.getUserNameFromJwtToken(jwtParser.parse(request)!!)
        ).notifications.toMono()
    }

    @PostMapping("/add")
    fun addNotifications(request: HttpServletRequest, @RequestParam content: String, @RequestParam type: String)
    {
        val user = userService.get(
            jwtUtils.getUserNameFromJwtToken(jwtParser.parse(request)!!)
        )

        var id = "notification-${hexConvertor.encode(user.email)}-"

        var counter = 1

        while (true)
        {
            val notification: Notification? = user.notifications!!.find { it.id == "$id$counter" }
            if (notification != null)
            {
                counter++
            }
            else
            {
                break
            }
        }

        id = "$id$counter"

        val newNotification = Notification(
            id,
            content,
            type,
            false,
            LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        )

        user.notifications!!.add(newNotification)

        userService.update(user)

        notificationsDispatcher.send(
            user.socketId!!,
            newNotification
        )
    }
}