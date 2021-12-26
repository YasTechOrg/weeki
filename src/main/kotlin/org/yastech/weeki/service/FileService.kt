package org.yastech.weeki.service

import com.mongodb.BasicDBObject
import com.mongodb.client.gridfs.model.GridFSFile
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.gridfs.ReactiveGridFsOperations
import org.springframework.data.mongodb.gridfs.ReactiveGridFsResource
import org.springframework.stereotype.Service
import org.yastech.weeki.data.HexConvertor
import org.yastech.weeki.data.ProductGenerator
import reactor.core.publisher.Flux
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Service
class FileService
(
    private var reactiveGridFsOperations: ReactiveGridFsOperations,
    private var hexConvertor: HexConvertor,
    private var productGenerator: ProductGenerator,
    private var productService: ProductService
)
{
    fun addProductImage(email: String, id: String, image: Flux<DataBuffer>, format: String): String
    {
        var fileName = "product_${hexConvertor.encode(id)}_${hexConvertor.encode(email)}_${format}_${productGenerator.generatePID()}"

        while (exitsProductImage(fileName))
        {
            fileName = "product_${hexConvertor.encode(id)}_${hexConvertor.encode(email)}_${format}_${productGenerator.generatePID()}"
        }

        val metaData = BasicDBObject()
        metaData["user"] = email
        metaData["id"] = id
        metaData["format"] = format
        metaData["date"] = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)

        productService.addImage(fileName, id)

        return reactiveGridFsOperations.store(
            image,
            fileName,
            metaData
        ).block()!!.toHexString()
    }

    fun exitsProductImage(id: String): Boolean
    {
        return reactiveGridFsOperations.findOne(Query(Criteria.where("filename").`is`(id))).block() !== null
    }

    fun getProductImage(id: String): ReactiveGridFsResource?
    {
        val file: GridFSFile? = reactiveGridFsOperations.findOne(Query(Criteria.where("filename").`is`(id))).block()
        return file?.filename?.let { reactiveGridFsOperations.getResource(it).block() }
    }
}