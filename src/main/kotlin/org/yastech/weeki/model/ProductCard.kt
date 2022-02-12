package org.yastech.weeki.model

data class ProductCard
(
    var id: String,
    var type: String,
    var family: String,
    var country: String,
    var city: String,
    var location: String,
    var code: String,
    var grade: String,
    var packing: String,
    var amount: Long,
    var ppk: Long,
    var images: MutableList<String>?,
    var bs: String,
    var description: String,
    var seller: String?,
    var company: String?,
)