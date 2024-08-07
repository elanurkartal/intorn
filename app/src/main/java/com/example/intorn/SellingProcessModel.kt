package com.example.intorn

data class SellingProcessModel(
    val name: String,
    val price: Double,
    var quantity: Int,
    val productId: Int, ) {
}