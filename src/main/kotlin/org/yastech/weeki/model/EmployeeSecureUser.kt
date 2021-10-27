package org.yastech.weeki.model

import org.yastech.weeki.data.USERS

data class EmployeeSecureUser
(
    var email: String,
    var firstname: String?,
    var lastname: String?,
    var company: String?,
    var role: USERS,
    var addTime: String?,
    var accepted: Boolean?,
    var access: MutableList<String>?,
)