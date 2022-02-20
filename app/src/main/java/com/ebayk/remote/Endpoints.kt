package com.ebayk.remote

import retrofit2.http.GET

interface Endpoints {

    @GET("candidate/ads/1118635128")
    suspend fun retrieveApartmentInfo(): Unit
}