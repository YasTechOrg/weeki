package org.yastech.weeki.data

import org.springframework.stereotype.Service
import org.yastech.weeki.model.*
import org.yastech.weeki.service.UserService
import org.yastech.weeki.table.Product
import org.yastech.weeki.table.User
import reactor.core.publisher.Flux

@Service
class SecureGenerator
(
    private var userService: UserService
)
{
    private fun getCompany(publisher: String, owner: String): String?
    {
        return if (publisher == owner)
        {
            userService.get(publisher).name
        }
        else
        {
            if (userService.get(publisher).role == USERS.NORMAL_USER)
            {
                null
            }
            else
            {
                userService.get(owner).name
            }
        }
    }

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

    fun generateProfileCard(products: Flux<Product>): Flux<ProductCard>
    {
        return products.map {

            val user = userService.get(it.publisher!!)

            val seller = if (it.publisher == it.owner)
            {
                null
            }
            else
            {
                "${user.firstname} ${user.lastname}"
            }

            val company = when(user.role)
            {
                USERS.NORMAL_USER -> {
                    null
                }
                USERS.EMPLOYEE -> {
                    userService.get(it.owner!!).name
                }
                else -> {
                    user.name
                }
            }

            ProductCard(
                it.id!!,
                it.type,
                it.family,
                it.country,
                it.city,
                it.location,
                it.code,
                it.grade,
                it.packing,
                it.amount,
                it.ppk,
                if (it.images!!.isEmpty()) null else it.images!![0],
                it.bs,
                it.description,
                seller,
                company
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
