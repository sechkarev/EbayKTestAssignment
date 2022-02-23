package com.ebayk.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ebayk.model.ApartmentInfoLoadingStatus
import com.ebayk.usecase.RetrieveApartmentInfo
import kotlinx.coroutines.launch

class MainViewModel(
    private val retrieveApartmentInfo: RetrieveApartmentInfo
) : ViewModel() {

    val apartmentInfo = MutableLiveData<ApartmentInfoLoadingStatus>()

    init {
        retrieveApartmentDetails()
    }

    fun onErrorMessageClick() {
        retrieveApartmentDetails()
    }

    private fun retrieveApartmentDetails() {
        viewModelScope.launch {
            apartmentInfo.value = ApartmentInfoLoadingStatus.Loading
            try {
                apartmentInfo.value = ApartmentInfoLoadingStatus.Success(
                    retrieveApartmentInfo()
                )
            } catch (t: Throwable) {
                apartmentInfo.value = ApartmentInfoLoadingStatus.Error(t)
            }
        }
    }
}