package org.yastech.weeki.model

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.yastech.weeki.table.User

class UserDetail
(
    private var email: String,
    private var password: String,
    private var firstname: String?,
    private var lastname: String?,
    private var authorities:  MutableCollection<out GrantedAuthority>,
    private var name: String?,
    private var notifications: MutableList<Notification>?,
    private var socketId: String?,
    private var phoneNumber: String,
    private var address: String?,
    private var city: String,
    private var country: String,
    private var company: String?,
    private var forgot: Boolean = false,
    private var forgotCode: String?,
    private var tasks: MutableList<Task>,
    private var access: MutableList<String>?,
    private var accepted: Boolean?,
    private var addTime: String?,
    private var contacts: MutableList<String>?,
    private var rate: MutableMap<String, Int>?,
    private var favoriteChats: MutableList<String>?,
    private var messageId: String?
) : UserDetails
{
    fun build(user: User): UserDetail
    {
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

    override fun getPassword(): String
    {
        return password
    }

    override fun getUsername(): String
    {
        return email
    }

    override fun isAccountNonExpired(): Boolean
    {
        return true
    }

    override fun isAccountNonLocked(): Boolean
    {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean
    {
        return true
    }

    override fun isEnabled(): Boolean
    {
        return true
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority>
    {
        return authorities
    }
}