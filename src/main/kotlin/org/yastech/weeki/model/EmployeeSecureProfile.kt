package org.yastech.weeki.model

data class EmployeeSecureProfile
(
    var email: String,
    var firstname: String?,
    var lastname: String?,
    var company: String?,
    var address: String?,
    var phoneNumber: String?,
    var rate: MutableMap<String, Int>?,
)