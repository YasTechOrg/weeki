package org.yastech.weeki.data

import org.springframework.stereotype.Service
import java.util.*

@Service
class FileUtils
{
    fun getExtension(name: String): Optional<String?>?
    {
        return Optional.ofNullable(name)
            .filter { f -> f.contains(".") }
            .map { f -> f.substring(name.lastIndexOf(".") + 1) }
    }
}