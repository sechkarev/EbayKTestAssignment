package com.ebayk.remote

import okhttp3.Credentials
import retrofit2.http.GET
import retrofit2.http.Header

interface Endpoints {

    @GET("candidate/ads/1118635128")
    suspend fun retrieveApartmentInfo(
        @Header("authorization") credentials: String = Credentials.basic("candidate", "yx6Xz62y"),
        @Header("user-agent") userAgent: String = "some-user-agent",
        @Header("accept") accept: String = "*/*",
    )
}