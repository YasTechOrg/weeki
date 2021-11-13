package org.yastech.weeki.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.yastech.weeki.data.JWTParser
import org.yastech.weeki.data.SecureGenerator
import org.yastech.weeki.model.MoreSecureUser
import org.yastech.weeki.model.SecureUser
import org.yastech.weeki.security.JWTUtils
import org.yastech.weeki.service.UserService
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
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
)
{
    @GetMapping("/user/get")
    fun getUser(request: HttpServletRequest): Mono<SecureUser>
    {
        return secureGenerator.generateSecureUser(
            userService.get(jwtUtils.getUserNameFromJwtToken(jwtParser.parse(request)!!))
        ).toMono()
    }

    @GetMapping("/company/all")
    fun getAllCompanies(): Flux<MoreSecureUser>
    {
        return secureGenerator.generateMoreSecureUserByFlux(userService.getCompanies())
    }
}