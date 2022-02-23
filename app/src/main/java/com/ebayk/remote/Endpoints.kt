package com.ebayk.remote

import com.ebayk.dto.ApartmentDetails
import retrofit2.http.GET
import retrofit2.http.Path

interface Endpoints {

    @GET("candidate/ads/{ad_id}")
    suspend fun retrieveApartmentInfo(@Path("ad_id") adId: String = "1118635128"): ApartmentDetails
}