package com.ebayk.usecase

import com.ebayk.remote.Endpoints

class RetrieveApartmentInfo(
    private val endpoints: Endpoints
) {

    suspend operator fun invoke() {
        endpoints.retrieveApartmentInfo()
    }
}