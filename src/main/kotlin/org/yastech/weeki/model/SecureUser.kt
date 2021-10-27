package org.yastech.weeki.model

import org.yastech.weeki.data.USERS

data class SecureUser
(
    var email: String,
    var firstname: String?,
    var lastname: String?,
    var phoneNumber: String,
    var address: String?,
    var city: String,
    var country: String,
    var company: String?,
    var role: USERS,
    var name: String?,
    var socketId: String?,
    var messageId: String?,
    var access: MutableList<String>?,
    var accepted: Boolean?,
    var contacts: MutableList<String>
)