package org.yastech.weeki.api

import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.yastech.weeki.service.FileService

@RestController
@RequestMapping("/api/rest/cdn")
class CDNRestController
(
    private var fileService: FileService
)
{
    @GetMapping("/product/images/{id}")
    fun getProductImage(@PathVariable id: String): ResponseEntity<ByteArray?>
    {
        return if (fileService.exitsProductImage(id))
        {
            val img = fileService.getProductImage(id)
            ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "filename=${img!!.filename}.${img.options.metadata["format"]}")
                .contentType(MediaType.IMAGE_PNG)
                .body(img.inputStream.block()!!.readAllBytes())
        }
        else
        {
            ResponseEntity.notFound().build()
        }
    }
}