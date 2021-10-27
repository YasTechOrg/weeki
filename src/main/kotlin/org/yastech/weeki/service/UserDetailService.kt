package org.yastech.weeki.service

import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import org.yastech.weeki.model.UserDetail

@Service
class UserDetailService
(
    private var userService: UserService
)
    : UserDetailsService
{
    override fun loadUserByUsername(username: String?): UserDetails
    {
        val user = userService.get(username!!)
        return UserDetail(
            user.email,
            user.password,
            user.firstname,
            user.lastname,
            mutableListOf(SimpleGrantedAuthority(user.role.name)),
            user.name,
            user.notifications,
            user.socketId,
            user.phoneNumber,
            user.address,
            user.city,
            user.country,
            user.company,
            user.forgot,
            user.forgotCode,
            user.tasks!!,
            user.access,
            user.accepted,
            user.addTime,
            user.contacts,
            user.rate,
            user.favoriteChats,
            user.messageId
        )
    }
}