package org.yastech.weeki

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class UrlHandler
{
    @RequestMapping(value = ["/{path:[^.]*}", "/account/{path:[^.]*}"])
    fun surface(@PathVariable path: String): String
    {
        return "forward:/"
    }

    @RequestMapping("/dashboard/**")
    fun dashboard(): String
    {
        return "forward:/"
    }
}