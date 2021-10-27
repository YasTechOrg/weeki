package org.yastech.weeki.model

import org.yastech.weeki.data.USERS

data class MoreSecureUser
(
    var email: String,
    var firstname: String?,
    var lastname: String?,
    var company: String?,
    var role: USERS,
    var name: String?,
)