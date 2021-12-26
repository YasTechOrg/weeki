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
@RequestMapping("/api/rest/account")
class AccountRestController
(
    private var userService: UserService,
    private var jwtParser: JWTParser,
    private var secureGenerator: SecureGenerator,
    private var jwtUtils: JWTUtils,
    private var hexConvertor: HexConvertor,
    private var productGenerator: ProductGenerator
)
{
    @GetMapping("/user/get")
    fun getUser(request: HttpServletRequest): Mono<SecureUser>
    {
        return secureGenerator.generateSecureUser(
            userService.get(jwtUtils.getUserNameFromJwtToken(jwtParser.parse(request)!!))
        ).toMono()
    }

    @PostMapping("/stars/update")
    fun updateStars(request: HttpServletRequest, @RequestParam id: String, @RequestParam rate: Int)
    {
        val user = userService.get(
            jwtUtils.getUserNameFromJwtToken(jwtParser.parse(request)!!)
        )
        val user2 = userService.get(id)

        user2.rate!![user.email] = rate

        userService.update(user2)
    }

    @GetMapping("/company/all")
    fun getAllCompanies(): Flux<MoreSecureUser>
    {
        return secureGenerator.generateMoreSecureUserByFlux(userService.getCompanies())
    }

    @GetMapping("/newspapers/self")
    fun getSelfNewspapers(request: HttpServletRequest): Flux<Newspaper>
    {
        val user = userService.get(
            jwtUtils.getUserNameFromJwtToken(jwtParser.parse(request)!!)
        )

        return user.newspapers!!.toFlux()
    }

    @PostMapping("/newspapers/add")
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

    @GetMapping("/sd/{id}")
    fun getSellerDetails(@PathVariable id: String): Mono<SellerDetail>
    {
        val seller = userService.get(id)
        when (seller.role)
        {
            USERS.NORMAL_USER -> {
                return SellerDetail(
                    null,
                    "${seller.firstname} ${seller.lastname}"
                ).toMono()
            }
            USERS.EMPLOYEE -> {
                return SellerDetail(
                    userService.get(seller.company!!).name,
                    "${seller.firstname} ${seller.lastname}"
                ).toMono()
            }
            else -> {
                return SellerDetail(
                    seller.name,
                    null
                ).toMono()
            }
        }
    }

    @PostMapping("/contact/add")
    fun addToContacts(request: HttpServletRequest, @RequestParam id: String)
    {
        val user = userService.get(
            jwtUtils.getUserNameFromJwtToken(jwtParser.parse(request)!!)
        )
        user.contacts!!.add(id)
        userService.update(user)
    }

    @PostMapping("/contact/remove")
    fun removeFromContacts(request: HttpServletRequest, @RequestParam id: String)
    {
        val user = userService.get(
            jwtUtils.getUserNameFromJwtToken(jwtParser.parse(request)!!)
        )
        user.contacts!!.remove(id)
        userService.update(user)
    }

    @GetMapping("/contact/get")
    fun getContacts(request: HttpServletRequest): Flux<SecureContact>
    {
        return secureGenerator.generateSecureContact(
            userService.get(
                jwtUtils.getUserNameFromJwtToken(jwtParser.parse(request)!!)
            ).contacts!!.map { userService.get(it) }.toFlux()
        )
    }
}