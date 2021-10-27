package org.yastech.weeki.data

import org.springframework.stereotype.Service

@Service
class DataConvertor
{
    // Convert Double To Int
    fun doubleToInt(value: Double) : Int
    {
        return value.toInt()
    }

    // Convert Double To Long
    fun doubleToLong(value: Double) : Long
    {
        return value.toLong()
    }
}