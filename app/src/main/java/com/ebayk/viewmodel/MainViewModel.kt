package com.ebayk.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ebayk.model.dto.ApartmentDetails
import com.ebayk.usecase.RetrieveApartmentInfo
import com.ebayk.model.ApartmentInfoLoadingStatus
import kotlinx.coroutines.launch
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

// todo: test this, use case and utils
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