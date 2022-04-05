package org.yastech.weeki.service

import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service
import org.yastech.weeki.data.HexConvertor
import org.yastech.weeki.data.ProductGenerator
import org.yastech.weeki.data.USERS
import org.yastech.weeki.table.Product
import org.yastech.weeki.table.ProductRepository
import reactor.core.publisher.Flux
import java.util.regex.Pattern

@Service
class ProductService
(
    private var repository: ProductRepository,
    private var productGenerator: ProductGenerator,
    private var userService: UserService,
    private var hexConvertor: HexConvertor,
    private var templateRepository: ReactiveMongoTemplate,
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

    fun update(product: Product)
    {

    }

    fun doSearch(
        bs: String,
        city: String?,
        code: String?,
        grade: String?,
        packing: String?,
        name: String?,
        min_am: Long,
        max_am: Long,
        min_ppk: Long,
        max_ppk: Long,
    ): MutableList<Product>?{

        // Check Buyer / Seller Value
        val filterQueries = Criteria.where("bs").`is`(bs)

        // City Partial Search With Regex + Null-Safe
        if (!city.isNullOrEmpty() || city != "")
        {
            filterQueries.and("city").regex(Pattern.compile(city!!, Pattern.CASE_INSENSITIVE))
        }

        // Check Grade Value + Null-Safe
        if (!grade.isNullOrEmpty() || grade != "")
        {
            filterQueries.and("grade").`is`(grade)
        }

        // Packing Partial Search With Regex + Null-Safe
        if (!packing.isNullOrEmpty() || packing != "")
        {
            filterQueries.and("packing").regex(Pattern.compile(packing!!, Pattern.CASE_INSENSITIVE))
        }

        // Check Code Value + Null-Safe
        if (!code.isNullOrEmpty() || code != "")
        {
            filterQueries.and("code").`is`(code)
        }

        // Check Amount Value Between
        filterQueries.and("amount").gte(min_am).lte(max_am)

        // Check Price Per Kg Value Between
        filterQueries.and("ppk").gte(min_ppk).lte(max_ppk)

        // Name Partial Search With Regex + Null-Safe
        if (!name.isNullOrEmpty() || name != "")
        {
            filterQueries.and("family").regex(Pattern.compile(name!!, Pattern.CASE_INSENSITIVE))
        }

        // Execute Query + Collect List
        return templateRepository.find(
            Query().addCriteria(filterQueries),
            Product::class.java
        ).collectList().block()
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