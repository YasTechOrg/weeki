package org.yastech.weeki.model

import org.yastech.weeki.data.STATE

data class Message
(
    var id: String,
    var sender: String,
    var receiver: String,
    var text: String?,
    var image: String?,
    var document: String?,
    var voice: String?,
    var state: STATE,
    var sendTime: String
)