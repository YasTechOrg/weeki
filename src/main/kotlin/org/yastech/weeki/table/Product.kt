package org.yastech.weeki.table

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.yastech.weeki.data.USERS
import org.yastech.weeki.model.Notification
import org.yastech.weeki.model.Task
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Document
data class Product
(
    @Id
    var id: String?,
    var type: String,
    var family: String,
    var country: String,
    var city: String,
    var location: String,
    var code: String,
    var grade: String,
    var publisher: String?,
    var owner: String?,
    var packing: String,
    var amount: Long,
    var ppk: Long,
    var images: MutableList<String>?,
    var bs: String,
    var description: String,
)

interface ProductRepository : ReactiveMongoRepository<Product, String>
{
    fun findAllByOwner(owner: String): Flux<Product>
}