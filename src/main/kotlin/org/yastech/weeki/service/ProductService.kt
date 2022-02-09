package org.yastech.weeki.service

import org.springframework.stereotype.Service
import org.yastech.weeki.data.HexConvertor
import org.yastech.weeki.data.ProductGenerator
import org.yastech.weeki.data.USERS
import org.yastech.weeki.table.Product
import org.yastech.weeki.table.ProductRepository
import reactor.core.publisher.Flux

@Service
class ProductService
(
    private var repository: ProductRepository,
    private var productGenerator: ProductGenerator,
    private var userService: UserService,
    private var hexConvertor: HexConvertor
)
{
    fun add(product: Product, publisher: String): String
    {
        var id = "product_${hexConvertor.encode(publisher)}_${productGenerator.generatePID()}"

        while (exits(id))
        {
            id = "product_${hexConvertor.encode(publisher)}_${productGenerator.generatePID()}"
        }

        product.id = id

        product.publisher = publisher

        val user = userService.get(publisher)

        if (user.role === USERS.EMPLOYEE)
        {
            product.owner = user.company
        }
        else
        {
            product.owner = publisher
        }

        product.images = mutableListOf()

        repository.save(product).subscribe()

        return id
    }

    fun addImage(id: String, p_id: String)
    {
        val product = repository.findById(p_id).block()!!
        product.images!!.add(id)
        repository.save(product).subscribe()
    }

    fun exits(id: String): Boolean
    {
        return repository.existsById(id).block()!!
    }

    fun get(id: String): Flux<Product>
    {
        return repository.findAllByOwner(id)
    }

    fun getById(id: String): Product
    {
        return repository.findById(id).block()!!
    }

    fun delete(id: String)
    {
        repository.deleteById(id).subscribe()
    }
}