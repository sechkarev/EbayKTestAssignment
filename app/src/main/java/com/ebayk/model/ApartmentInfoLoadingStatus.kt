package com.ebayk.model

import com.ebayk.model.dto.ApartmentDetails

sealed interface ApartmentInfoLoadingStatus {
    object Loading : ApartmentInfoLoadingStatus
    class Error(val throwable: Throwable) : ApartmentInfoLoadingStatus
    class Success(val data: ApartmentDetails) : ApartmentInfoLoadingStatus
}
