package org.yastech.weeki.api

import org.springframework.web.bind.annotation.*
import org.yastech.weeki.data.HexConvertor
import org.yastech.weeki.data.IMPORTANCE
import org.yastech.weeki.data.JWTParser
import org.yastech.weeki.model.Task
import org.yastech.weeki.security.JWTUtils
import org.yastech.weeki.service.UserService
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/api/rest/tasks")
class TaskRestController
(
    private var userService: UserService,
    private var jwtParser: JWTParser,
    private var jwtUtils: JWTUtils,
    private var hexConvertor: HexConvertor,
)
{

    @PostMapping("/add")
    fun addTask(request: HttpServletRequest,
                @RequestParam title: String,
                @RequestParam details: String,
                @RequestParam time: String,
                @RequestParam notification: Boolean,
                @RequestParam importance: Int)
    {
        var newImportance = IMPORTANCE.MEDIUM

        when (importance)
        {
            1 -> newImportance = IMPORTANCE.LOW
            2 -> newImportance = IMPORTANCE.MEDIUM
            3 -> newImportance = IMPORTANCE.HIGH
        }

        val user = userService.get(jwtUtils.getUserNameFromJwtToken(jwtParser.parse(request)!!))

        var id = "task-${hexConvertor.encode(user.email)}-"

        var counter = 1

        while (true)
        {
            val task: Task? = user.tasks!!.find { it.id == "$id$counter" }
            if (task != null)
            {
                counter++
            }
            else
            {
                break
            }
        }

        id = "$id$counter"

        val newTask = Task(
            id,
            title,
            details,
            time,
            false,
            newImportance,
            LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE),
            notification
        )

        user.tasks!!.add(newTask)

        userService.update(user)
    }

    @PostMapping("/update")
    fun updateTask(request: HttpServletRequest,
                   @RequestParam id: String,
                   @RequestParam title: String,
                   @RequestParam details: String,
                   @RequestParam time: String,
                   @RequestParam notification: Boolean,
                   @RequestParam importance: Int,
                   @RequestParam date: String)
    {
        var newImportance = IMPORTANCE.MEDIUM

        when (importance)
        {
            1 -> newImportance = IMPORTANCE.LOW
            2 -> newImportance = IMPORTANCE.MEDIUM
            3 -> newImportance = IMPORTANCE.HIGH
        }

        val user = userService.get(jwtUtils.getUserNameFromJwtToken(jwtParser.parse(request)!!))

        val nt: Task? = user.tasks!!.find { it.id == id }

        if(nt != null)
        {
            val newTask = Task(
                id,
                title,
                details,
                time,
                nt.checked,
                newImportance,
                date,
                notification
            )
            user.tasks!![user.tasks!!.indexOf(nt)] = newTask
        }

        userService.update(user)
    }

    @GetMapping("/get")
    fun getTask(request: HttpServletRequest): Mono<MutableList<Task>>
    {
        val tasks = userService.get(
            jwtUtils.getUserNameFromJwtToken(jwtParser.parse(request)!!)
        ).tasks

        val todayTask = mutableListOf<Task>()
        tasks?.forEach {
            if (it.date == LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE))
            {
                todayTask.add(it)
            }
        }

        return todayTask.toMono()
    }

    @PostMapping("/tick")
    fun tickTask(request: HttpServletRequest, @RequestParam id: String, @RequestParam check: Boolean)
    {
        val user = userService.get(jwtUtils.getUserNameFromJwtToken(jwtParser.parse(request)!!))

        user.tasks!!.forEach {
            if (it.id == id)
            {
                it.checked = check
            }
        }

        userService.update(user)
    }

    @PostMapping("/delete")
    fun deleteTask(request: HttpServletRequest, @RequestParam id: String)
    {
        val user = userService.get(jwtUtils.getUserNameFromJwtToken(jwtParser.parse(request)!!))

        var item: Task = user.tasks!![0]

        user.tasks!!.forEach {
            if (it.id == id)
            {
                item = it
            }
        }

        user.tasks!!.remove(item)

        userService.update(user)
    }
}