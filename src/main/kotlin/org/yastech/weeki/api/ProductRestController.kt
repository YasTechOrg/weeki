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

    @GetMapping("/get")
    fun get(request: HttpServletRequest): Flux<ProductCard>
    {
        val user = userService.get(
            jwtUtils.getUserNameFromJwtToken(jwtParser.parse(request)!!)
        )

        val products = productService.get(if (user.role === USERS.EMPLOYEE) user.company!! else user.email)

        return products.map {

            val seller = if (it.publisher == it.owner)
            {
                null
            }
            else
            {
                "${user.firstname} ${user.lastname}"
            }

            val company = when(user.role)
            {
                USERS.NORMAL_USER -> {
                    null
                }
                USERS.EMPLOYEE -> {
                    userService.get(it.owner!!).name
                }
                else -> {
                    user.name
                }
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
                if (it.images!!.isEmpty()) null else it.images!![0],
                it.bs,
                it.description,
                seller,
                company
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