package org.yastech.weeki.data

import org.springframework.stereotype.Service
import java.util.*
import kotlin.streams.asSequence

@Service
class ForgotGenerator
{
    fun generate(): String
    {
        val source = "abcdefghijklmnopqrstuvwxyz123456789"

        return Random().ints(30, 0, source.length)

            .asSequence()

            .map(source::get)

            .joinToString("")
    }
}