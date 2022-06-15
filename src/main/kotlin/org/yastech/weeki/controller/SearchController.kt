package org.yastech.weeki.controller

import com.google.gson.Gson
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.simp.stomp.StompHeaderAccessor
import org.springframework.stereotype.Controller
import org.yastech.weeki.data.DataConvertor
import org.yastech.weeki.data.USERS
import org.yastech.weeki.dispatcher.SearchDispatcher
import org.yastech.weeki.model.ProductCard
import org.yastech.weeki.service.ProductService
import org.yastech.weeki.service.UserService


@Controller
class SearchController(
    private val searchDispatcher: SearchDispatcher,
    private val productService: ProductService,
    private val dataConvertor: DataConvertor,
    private val userService: UserService
) {

    @MessageMapping("/do-search")
    fun start(stompHeaderAccessor: StompHeaderAccessor)
    {
        // Get Search Values And Convert To Map
        val searchItems = Gson()
            .fromJson(
                stompHeaderAccessor.getNativeHeader("search")!![0].toString(),
                Map::class.java
            )

        val bs = dataConvertor.doubleToInt(searchItems["bs"] as Double)

        // Execute Search Query
        val searchResult = productService.doSearch(
            if (bs == 1) "buyer" else "seller",
            searchItems["pc"].toString(),
            searchItems["pcd"].toString(),
            searchItems["pg"].toString(),
            searchItems["pp"].toString(),
            searchItems["text"].toString(),
            dataConvertor.doubleToLong(searchItems["min_am"] as Double),
            dataConvertor.doubleToLong(searchItems["max_am"] as Double),
            dataConvertor.doubleToLong(searchItems["min_ppk"] as Double),
            dataConvertor.doubleToLong(searchItems["max_ppk"] as Double),
        )

        // Collect Map Result
        val mapResult =  searchResult!!.map {

            val publisher = userService.get(it.publisher!!)
            val seller = if (it.publisher == it.owner) "" else "${publisher.firstname} ${publisher.lastname}"

            val company = when(publisher.role)
            {
                USERS.NORMAL_USER -> null
                USERS.EMPLOYEE -> userService.get(it.owner!!).name
                else -> publisher.name
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

        val page = stompHeaderAccessor.getNativeHeader("page")!![0].toInt()

        val indexItems = mapResult.chunked(6)

        // Convert Result To Json
        val jsonSearchResult = Gson()
            .toJson({
                if(indexItems.isNotEmpty()) mutableListOf(indexItems.size, indexItems[page - 1])
                else mutableListOf(0, emptyList<Any>())
            })

        // Send Result To Client
        searchDispatcher.send(stompHeaderAccessor.sessionId!!, jsonSearchResult)

    }
}