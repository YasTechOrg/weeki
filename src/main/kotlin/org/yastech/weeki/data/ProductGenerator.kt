package org.yastech.weeki.data

import org.springframework.stereotype.Service
import java.util.*
import kotlin.streams.asSequence

@Service
class ProductGenerator
{
    fun generatePID(): String
    {
        val source = "abcdefghijklmnopqrstuvwxyz123456789"

        return Random().ints(10, 0, source.length)

            .asSequence()

            .map(source::get)

            .joinToString("")
    }
}