package com.ebayk.model.dto

import com.google.gson.annotations.SerializedName
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

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
) {
    val pictureUrls
        get() = pictures.map {
            PictureUrls(
                it.replace("{imageId}", "1"),
                it.replace("{imageId}", "57"),
            )
        }
    //todo: photos not always exist: https://i.ebayimg.com/00/s/MTA2NlgxNjAw/z/w4sAAOSwYRJhSgCD/$_1.JPG is empty, https://i.ebayimg.com/00/s/MTA2NlgxNjAw/z/w4sAAOSwYRJhSgCD/$_57.JPG is not

    val formattedPostDate
        get() = postDate.substringBefore("T").let {
            try {
                SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(it)?.let { date ->
                    SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(date)
                } ?: "-"
            } catch (parseException: ParseException) {
                "-"
            }
        }
}

data class PictureUrls(
    val previewUrl: String,
    val fullSizeUrl: String,
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