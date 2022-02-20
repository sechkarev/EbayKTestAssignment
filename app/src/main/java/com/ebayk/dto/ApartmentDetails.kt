package com.ebayk.dto

import com.google.gson.annotations.SerializedName

data class ApartmentDetails(
    val title: String,
    val price: Price,
    val address: Address,
    @SerializedName("posted-date-time") val postDate: String,
    val visits: Int,
    val id: String,
    val description: String,
    val attributes: List<Attribute>,
    val features: List<String>,
    val pictures: List<String>,
    val documents: List<Document>,
)

data class Price(
    val currency: String,
    val amount: Int,
)

data class Address(
    val street: String,
    val city: String,
    @SerializedName("zip-code") val zipCode: String,
    val longitude: String,
    val latitude: String,
)

data class Attribute(
    val label: String,
    val value: String,
    val unit: String? = null,
)

data class Document(
    val link: String,
    val title: String,
)