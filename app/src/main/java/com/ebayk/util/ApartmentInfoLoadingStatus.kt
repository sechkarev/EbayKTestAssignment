package com.ebayk.util

import com.ebayk.dto.ApartmentDetails

sealed interface ApartmentInfoLoadingStatus {
    object Loading : ApartmentInfoLoadingStatus
    object Error : ApartmentInfoLoadingStatus
    class Success(val data: ApartmentDetails) : ApartmentInfoLoadingStatus
}
