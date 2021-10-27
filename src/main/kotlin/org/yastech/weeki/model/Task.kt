package org.yastech.weeki.model

import org.yastech.weeki.data.IMPORTANCE

data class Task
(
    var id: String,
    var title: String,
    var details: String,
    var time: String,
    var checked: Boolean,
    var importance: IMPORTANCE,
    var date: String,
    var notification: Boolean
)