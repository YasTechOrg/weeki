package org.yastech.weeki.model

data class ChatSecure
(
    var id: String,
    var receiver: String,
    var receiverName: String,
    var unread: Int,
    var lastMessage: String,
    var lastOnline: String
)