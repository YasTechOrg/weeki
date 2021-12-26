package org.yastech.weeki.table

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.yastech.weeki.data.USERS
import org.yastech.weeki.model.Newspaper
import org.yastech.weeki.model.Notification
import org.yastech.weeki.model.Task
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Document
data class User
(
    @Id
    var email: String,
    var password: String,
    var firstname: String?,
    var lastname: String?,
    var phoneNumber: String,
    var address: String?,
    var city: String,
    var country: String,
    var company: String?,
    var forgot: Boolean = false,
    var forgotCode: String?,
    var role: USERS,
    var name: String?,
    var tasks: MutableList<Task>?,
    var notifications: MutableList<Notification>?,
    var newspapers: MutableList<Newspaper>?,
    var socketId: String?,
    var access: MutableList<String>?,
    var accepted: Boolean?,
    var addTime: String?,
    var rate: MutableMap<String, Int>?,
    var contacts: MutableList<String>?,
    var messageId: String?,
    var favoriteChats: MutableList<String>?
)

interface UserRepository : ReactiveMongoRepository<User, String>
{
    fun findAllByRole(role: USERS): Flux<User>
    fun existsByForgotCode(forgotCode: String): Mono<Boolean>
    fun findByForgotCode(forgotCode: String): Mono<User>
    fun findAllByRoleAndCompany(role: USERS, company: String): Flux<User>
}