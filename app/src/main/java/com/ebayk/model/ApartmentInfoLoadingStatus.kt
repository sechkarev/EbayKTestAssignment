package com.ebayk.model

import com.ebayk.model.dto.ApartmentDetails

sealed interface ApartmentInfoLoadingStatus {
    object Loading : ApartmentInfoLoadingStatus
    object Error : ApartmentInfoLoadingStatus
    class Success(val data: ApartmentDetails) : ApartmentInfoLoadingStatus
}
