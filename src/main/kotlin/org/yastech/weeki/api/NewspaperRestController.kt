package org.yastech.weeki.api

import org.springframework.web.bind.annotation.*
import org.yastech.weeki.data.*
import org.yastech.weeki.model.*
import org.yastech.weeki.security.JWTUtils
import org.yastech.weeki.service.UserService
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux
import reactor.kotlin.core.publisher.toMono
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/api/rest/newspaper")
class NewspaperRestController
(
    private var userService: UserService,
    private var jwtParser: JWTParser,
    private var secureGenerator: SecureGenerator,
    private var jwtUtils: JWTUtils,
    private var hexConvertor: HexConvertor,
    private var productGenerator: ProductGenerator
)
{

    @GetMapping("/get")
    fun getSelfNewspapers(request: HttpServletRequest): Flux<Newspaper>
    {
        val user = userService.get(
            jwtUtils.getUserNameFromJwtToken(jwtParser.parse(request)!!)
        )

        return user.newspapers!!.toFlux()
    }

    @PostMapping("/add")
    fun addNewspaper(request: HttpServletRequest, @RequestParam name: String, @RequestParam url: String)
    {
        val user = userService.get(
            jwtUtils.getUserNameFromJwtToken(jwtParser.parse(request)!!)
        )
        user.newspapers!!.add(Newspaper(
            "newspaper-${hexConvertor.encode(user.email)}-${productGenerator.generatePID()}",
            name,
            url
        ))

        userService.update(user)
    }

    @PostMapping("/update")
    fun updateNewspaper(request: HttpServletRequest, @RequestParam index: Int, @RequestParam name: String, @RequestParam url: String)
    {
        val user = userService.get(
            jwtUtils.getUserNameFromJwtToken(jwtParser.parse(request)!!)
        )

        user.newspapers!![index].name = name
        user.newspapers!![index].url = url

        userService.update(user)
    }

    @PostMapping("/remove")
    fun removeNewspaper(request: HttpServletRequest, @RequestParam index: Int)
    {
        val user = userService.get(
            jwtUtils.getUserNameFromJwtToken(jwtParser.parse(request)!!)
        )

        user.newspapers!!.removeAt(index)

        userService.update(user)
    }
}