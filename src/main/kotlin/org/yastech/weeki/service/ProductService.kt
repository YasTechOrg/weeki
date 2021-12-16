package org.yastech.weeki.service

import org.springframework.stereotype.Service
import org.yastech.weeki.data.ProductGenerator
import org.yastech.weeki.table.Product
import org.yastech.weeki.table.ProductRepository

@Service
class ProductService
(
    private var repository: ProductRepository,
    private var productGenerator: ProductGenerator,
)
{
    fun add(product: Product, publisher: String): String
    {
        var id = "product_${publisher}_${productGenerator.generatePID()}"

        while (exits(id))
        {
            id = "product_${publisher}_${productGenerator.generatePID()}"
        }

        product.id = id

        product.publisher = publisher

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
}