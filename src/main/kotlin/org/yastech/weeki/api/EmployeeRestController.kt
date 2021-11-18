package org.yastech.weeki.api

import com.google.gson.Gson
import org.springframework.web.bind.annotation.*
import org.yastech.weeki.data.SecureGenerator
import org.yastech.weeki.model.EmployeeSecureProfile
import org.yastech.weeki.model.EmployeeSecureUser
import org.yastech.weeki.service.UserService
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/api/rest/employee")
class EmployeeRestController
(
    private var userService: UserService,
    private var secureGenerator: SecureGenerator,
)
{
    @PostMapping("/actions")
    fun actions(request: HttpServletRequest, @RequestParam id: String, @RequestParam action: String)
    {
        // ToDo : Complete In Deploy Time
        /*val user = userService.get(
            jwtUtils.getUserNameFromJwtToken(jwtParser.parse(request)!!)
        )*/
        when(action)
        {
            "reject" -> {
                userService.delete(id)
                // emailSender.sendRejectEmail(id, user.name!!)
            }
            "accept" -> {
                val employee = userService.get(id)
                employee.accepted = true
                userService.update(employee)
                // emailSender.sendAcceptEmail(id, user.name!!, request)
            }
        }
    }

    @GetMapping("/all/{company}")
    fun getAll(request: HttpServletRequest, @PathVariable company: String): Flux<EmployeeSecureUser>
    {
        return secureGenerator.generateSecureEmployeeByFlux(
            userService.getCompanyEmployees(company)
        )
    }

    @PostMapping("/remove")
    fun remove(request: HttpServletRequest, @RequestParam id: String)
    {
        userService.delete(id)
    }

    @GetMapping("/get/{id}")
    fun get(request: HttpServletRequest, @PathVariable id: String): Mono<EmployeeSecureProfile>
    {
        return secureGenerator.generateSecureProfileEmployee(userService.get(id)).toMono()
    }

    @PostMapping("/access")
    fun employeeUpdateAccess(request: HttpServletRequest, @RequestParam id: String, @RequestParam access: String)
    {
        val employee = userService.get(id)
        val items = Gson().fromJson(access, MutableList::class.java)

        employee.access!!.clear()

        items.forEach {
            employee.access!!.add(it.toString())
        }

        userService.update(employee)
    }
}