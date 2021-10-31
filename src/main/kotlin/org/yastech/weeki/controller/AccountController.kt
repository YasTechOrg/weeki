package org.yastech.weeki.controller

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.yastech.weeki.data.ForgotGenerator
import org.yastech.weeki.data.HexConvertor
import org.yastech.weeki.data.PasswordEncoder
import org.yastech.weeki.data.USERS
import org.yastech.weeki.model.Notification
import org.yastech.weeki.security.JWTUtils
import org.yastech.weeki.security.UserAuthenticator
import org.yastech.weeki.service.UserService
import org.yastech.weeki.table.User
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.servlet.http.HttpServletRequest

@Controller
@RequestMapping("/api/controller/account")
class AccountController
(
    private var userService: UserService,
    private var passwordEncoder: PasswordEncoder,
    private var userAuthenticator: UserAuthenticator,
    private var jwtUtils: JWTUtils,
    private var forgotGenerator: ForgotGenerator,
    /*private var emailSender: EmailSender,
    private var notificationsDispatcher: NotificationsDispatcher,*/
    private var hexConvertor: HexConvertor
)
{
    @PostMapping("/login")
    fun login(@RequestParam email: String, @RequestParam password: String): String
    {
        return if (userService.exist(email))
        {
            if (!userService.get(email).forgot)
            {
                if (passwordEncoder.encoder().matches(password, userService.get(email).password))
                {
                    if (userService.get(email).role == USERS.EMPLOYEE && !userService.get(email).accepted!!)
                    {
                        "redirect:/account/login?res=err_ac"
                    }
                    else
                    {
                        val authentication = userAuthenticator.getAuthenticate(email, password)

                        SecurityContextHolder.getContext().authentication = authentication

                        val token = jwtUtils.generateJwtToken(authentication)

                        "redirect:/dashboard?at=$token"
                    }
                }
                else
                {
                    "redirect:/account/login?res=err"
                }
            }
            else
            {
                "redirect:/account/login?res=safe"
            }
        }
        else
        {
            "redirect:/account/login?res=exi"
        }
    }

    @PostMapping("/register")
    fun register(@ModelAttribute("user") user: User, bindingResult: BindingResult): String
    {
        return if (!userService.exist(user.email))
        {
            userService.add(user)

            if(user.role == USERS.EMPLOYEE)
            {
                val company = userService.get(user.company!!)

                var id = "notification-${hexConvertor.encode(company.email)}-"

                var counter = 1

                while (true)
                {
                    val notification: Notification? = company.notifications!!.find { it.id == "$id$counter" }

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
                    "There is one new employee request!",
                    "notification",
                    false,
                    LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                )

                company.notifications!!.add(newNotification)

                userService.update(company)

                /*notificationsDispatcher.send(
                    company.socketId!!,
                    newNotification
                )*/
                "redirect:/account/login?res=d_reg_emp"
            }
            else
            {
                "redirect:/account/login?res=d_reg"
            }
        }
        else
        {
            "redirect:/account/register?res=exi"
        }
    }

    @PostMapping("/forgot")
    fun forgot(@RequestParam email: String, httpServletRequest: HttpServletRequest): String
    {
        return if (userService.exist(email))
        {
            val forgotUser = userService.get(email)

            val forgotCode = forgotGenerator.generate()

            forgotUser.forgotCode = forgotCode

            userService.update(forgotUser)

            /*emailSender.sendForgotEmail(email, forgotCode, httpServletRequest)*/

            "redirect:/account/login?res=f_send"
        }
        else
        {
            "redirect:/account/login?res=f_n_exi"
        }
    }



    // Change Password
    @PostMapping("/forgot/do")
    fun doForgot(@RequestParam token: String, @RequestParam password: String, @RequestParam c_password: String): String
    {
        return if(password == c_password)
        {
            val forgotUser = userService.getByForgotCode(token)

            if (passwordEncoder.encoder().matches(password, forgotUser.password))
            {
                "redirect:/account/forgot?u=$token&resp=fg_pe"
            }
            else
            {
                forgotUser.forgot = false

                forgotUser.forgotCode = null

                forgotUser.password = passwordEncoder.encoder().encode(password)

                userService.update(forgotUser)

                "redirect:/account/login?res=fg_done"
            }
        }
        else
        {
            "redirect:/account/forgot?u=$token&resp=fg_sa"
        }
    }
}