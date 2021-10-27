package org.yastech.weeki.model

data class Notification
(
    var id: String,
    var content: String,
    var type: String,
    var seen: Boolean,
    var time: String,
)