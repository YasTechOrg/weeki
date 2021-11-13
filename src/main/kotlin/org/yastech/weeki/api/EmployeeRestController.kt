package org.yastech.weeki.api

import org.springframework.web.bind.annotation.*
import org.yastech.weeki.data.SecureGenerator
import org.yastech.weeki.model.EmployeeSecureUser
import org.yastech.weeki.service.UserService
import reactor.core.publisher.Flux
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/api/rest/employee")
class EmployeeRestController
(
    private var userService: UserService,
    private var secureGenerator: SecureGenerator,
)
{
    @GetMapping("/actions")
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

    @GetMapping("/{company}")
    fun getAll(request: HttpServletRequest, @PathVariable company: String): Flux<EmployeeSecureUser>
    {
        return secureGenerator.generateSecureEmployeeByFlux(
            userService.getCompanyEmployees(company)
        )
    }
}