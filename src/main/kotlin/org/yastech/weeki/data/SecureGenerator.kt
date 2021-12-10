package org.yastech.weeki.data

import org.springframework.stereotype.Service
import org.yastech.weeki.model.*
import org.yastech.weeki.service.UserService
import org.yastech.weeki.table.User
import reactor.core.publisher.Flux

@Service
class SecureGenerator
(
    private var userService: UserService
)
{
    fun generateSecureUser(user: User): SecureUser
    {
        return SecureUser (
            user.email,
            user.firstname,
            user.lastname,
            user.phoneNumber,
            user.address,
            user.city,
            user.country,
            user.company,
            user.role,
            user.name,
            user.socketId,
            user.messageId,
            user.access,
            user.accepted,
            user.contacts!!
        )
    }

    fun generateSecureEmployee(user: User): EmployeeSecureUser
    {
        return EmployeeSecureUser (
            user.email,
            user.firstname,
            user.lastname,
            user.company,
            user.role,
            user.addTime,
            user.accepted,
            user.access
        )
    }

    fun generateSecureProfileEmployee(user: User): EmployeeSecureProfile
    {
        return EmployeeSecureProfile (
            user.email,
            user.firstname,
            user.lastname,
            user.company,
            userService.get(user.company!!).address,
            user.phoneNumber,
            user.rate
        )
    }

    fun generateSecureContact(users: Flux<User>): Flux<SecureContact>
    {
        return users.map {
            SecureContact(
                it.email,
                it.firstname,
                it.lastname,
                when (it.role)
                {
                    USERS.COMPANY ->
                    {
                        it.name!!
                    }
                    USERS.EMPLOYEE ->
                    {
                        userService.get(it.company!!).name!!
                    }
                    else ->
                    {
                        "User"
                    }
                },
                it.role
            )
        }
    }

    fun generateSecureUserByFlux(users: Flux<User>): Flux<SecureUser>
    {
        return users.map {
            SecureUser(
                it.email,
                it.firstname,
                it.lastname,
                it.phoneNumber,
                it.address,
                it.city,
                it.country,
                it.company,
                it.role,
                it.name,
                it.socketId,
                it.messageId,
                it.access,
                it.accepted,
                it.contacts!!,
            )
        }
    }

    fun generateSecureEmployeeByFlux(users: Flux<User>): Flux<EmployeeSecureUser>
    {
        return users.map {
            EmployeeSecureUser (
                it.email,
                it.firstname,
                it.lastname,
                it.company,
                it.role,
                it.addTime,
                it.accepted,
                it.access
            )
        }
    }

    fun generateMoreSecureUserByFlux(users: Flux<User>): Flux<MoreSecureUser>
    {
        return users.map {
            MoreSecureUser(
                it.email,
                it.firstname,
                it.lastname,
                it.company,
                it.role,
                it.name,
            )
        }
    }
}