package org.yastech.weeki.api

import org.springframework.core.io.buffer.DataBufferUtils
import org.springframework.core.io.buffer.DefaultDataBufferFactory
import org.springframework.http.MediaType
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.yastech.weeki.data.FileUtils
import org.yastech.weeki.data.JWTParser
import org.yastech.weeki.data.USERS
import org.yastech.weeki.model.ProductCard
import org.yastech.weeki.security.JWTUtils
import org.yastech.weeki.service.FileService
import org.yastech.weeki.service.ProductService
import org.yastech.weeki.service.UserService
import org.yastech.weeki.table.Product
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import java.io.InputStream
import java.util.concurrent.Callable
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/api/rest/product")
class ProductRestController
(
    private var userService: UserService,
    private var jwtParser: JWTParser,
    private var jwtUtils: JWTUtils,
    private var fileService: FileService,
    private var fileUtils: FileUtils,
    private var productService: ProductService
)
{
    @PostMapping("/image/{p_id}" , consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun upload(request: HttpServletRequest, @RequestParam file: MultipartFile, @PathVariable p_id: String): Mono<String>
    {
        val user = userService.get(
            jwtUtils.getUserNameFromJwtToken(jwtParser.parse(request)!!)
        )
        val callableInputStream : Callable<InputStream> = Callable { file.inputStream }
        val dataBuffer = DataBufferUtils.readInputStream(callableInputStream, DefaultDataBufferFactory(),4096)
        return fileService.addProductImage(user.email, p_id, dataBuffer, fileUtils.getExtension(file.originalFilename!!)?.get()!!).toMono()
    }

    @PostMapping("/add")
    fun add(request: HttpServletRequest, @ModelAttribute("product") product: Product, bindingResult: BindingResult): Mono<String>
    {
        val user = userService.get(
            jwtUtils.getUserNameFromJwtToken(jwtParser.parse(request)!!)
        )
        return productService.add(product, user.email).toMono()
    }

    @PostMapping("/update")
    fun update(
        @RequestParam id: String,
        @RequestParam files: MutableList<String>,
        @RequestParam packing: String,
        @RequestParam location: String,
        @RequestParam amount: Long,
        @RequestParam ppk: Long,
        @RequestParam description: String
    )
    {
        val product = productService.getById(id)
        product.let {
            it.packing = packing
            it.location = location
            it.amount = amount
            it.ppk = ppk
            it.description = description
            files.forEach { image ->
                it.images!!.add(image)
            }
        }
        productService.update(product)
    }

    @GetMapping("/get/single/{id}")
    fun getSingle(request: HttpServletRequest, @PathVariable id: String): Mono<ProductCard>
    {
        val product = productService.getById(id)

        val user = userService.get(product.publisher!!)

        val seller = if (product.publisher == product.owner) "" else "${user.firstname} ${user.lastname}"

        val company = when(user.role)
        {
            USERS.NORMAL_USER -> null
            USERS.EMPLOYEE -> userService.get(product.owner!!).name
            else -> user.name
        }

        return ProductCard(
            product.id!!,
            product.type,
            product.family,
            product.country,
            product.city,
            product.location,
            product.code,
            product.grade,
            product.packing,
            product.amount,
            product.ppk,
            if (product.images!!.isEmpty()) mutableListOf() else product.images!!,
            product.bs,
            product.description,
            seller,
            company,
            product.date
        ).toMono()
    }

    @GetMapping("/get")
    fun get(request: HttpServletRequest): Flux<ProductCard>
    {
        val user = userService.get(
            jwtUtils.getUserNameFromJwtToken(jwtParser.parse(request)!!)
        )

        val products = productService.get(if (user.role === USERS.EMPLOYEE) user.company!! else user.email)

        return products.map {

            val seller = if (it.publisher == it.owner) "" else "${user.firstname} ${user.lastname}"

            val company = when(user.role)
            {
                USERS.NORMAL_USER -> null
                USERS.EMPLOYEE -> userService.get(it.owner!!).name
                else -> user.name
            }

            ProductCard(
                it.id!!,
                it.type,
                it.family,
                it.country,
                it.city,
                it.location,
                it.code,
                it.grade,
                it.packing,
                it.amount,
                it.ppk,
                if (it.images!!.isEmpty()) mutableListOf() else it.images!!,
                it.bs,
                it.description,
                seller,
                company,
                it.date
            )
        }
    }

    @PostMapping("/remove")
    fun remove(request: HttpServletRequest, @RequestParam id: String)
    {
        val product = productService.getById(id)

        if (product.images!!.isNotEmpty())
        {
            product.images!!.forEach {
                fileService.removeProductImage(it)
            }
        }

        productService.delete(id)
    }
}