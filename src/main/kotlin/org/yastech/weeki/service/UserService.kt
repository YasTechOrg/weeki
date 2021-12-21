package org.yastech.weeki.service

import org.springframework.stereotype.Service
import org.yastech.weeki.data.HexConvertor
import org.yastech.weeki.data.PasswordEncoder
import org.yastech.weeki.data.USERS
import org.yastech.weeki.table.User
import org.yastech.weeki.table.UserRepository
import reactor.core.publisher.Flux
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Service
class UserService
(
    private var repository: UserRepository,
    private var passwordEncoder: PasswordEncoder,
    private var hexConvertor: HexConvertor
)
{
    fun add(user: User)
    {
        user.password = passwordEncoder.encoder().encode(user.password)

        user.forgot = false

        user.forgotCode = null

        user.notifications = mutableListOf()

        user.newspapers = mutableListOf()

        user.contacts = mutableListOf()

        user.rate = mutableMapOf()

        user.access = mutableListOf()

        user.favoriteChats = mutableListOf()

        user.socketId = hexConvertor.encode(user.email)

        user.messageId = hexConvertor.encode("msg_${user.email}")

        user.tasks = mutableListOf()

        if(user.role == USERS.EMPLOYEE)
        {
            user.accepted = false
            user.addTime = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        }

        repository.save(user).subscribe()
    }

    fun update(user: User)
    {
        repository.save(user).subscribe()
    }

    fun get(id: String): User
    {
        return repository.findById(id).block()!!
    }

    fun getAll(): Flux<User>
    {
        return repository.findAll()
    }

    fun getByForgotCode(code: String): User
    {
        return repository.findByForgotCode(code).block()!!
    }

    fun exist(id: String): Boolean
    {
        return repository.existsById(id).block()!!
    }

    fun existByForgot(code: String): Boolean
    {
        return repository.existsByForgotCode(code).block()!!
    }

    fun delete(id: String){
        repository.deleteById(id).subscribe()
    }

    fun getCompanies(): Flux<User>
    {
        return repository.findAllByRole(USERS.COMPANY)
    }

    fun getSellers(): Flux<User>
    {
        return repository.findAllByRole(USERS.NORMAL_USER)
    }

    fun getCompanyEmployees(company: String): Flux<User>
    {
        return repository.findAllByRoleAndCompany(USERS.EMPLOYEE, company)
    }
}