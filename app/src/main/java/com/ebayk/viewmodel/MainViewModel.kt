package com.ebayk.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ebayk.dto.ApartmentDetails
import com.ebayk.usecase.RetrieveApartmentInfo
import kotlinx.coroutines.launch

class MainViewModel(
    retrieveApartmentInfo: RetrieveApartmentInfo
) : ViewModel() {

    val apartmentInfo = MutableLiveData<ApartmentDetails?>()

    init {
        viewModelScope.launch {
            try {
                apartmentInfo.value = retrieveApartmentInfo().let { apartmentDetails ->
                    apartmentDetails.copy(
                        pictures = apartmentDetails.pictures.map { pictureUrl ->
                            pictureUrl.replace("{imageId}", "1") //todo: photos not always exist
                        }
                    )
                }
            } catch (t: Throwable) {
                Log.e("MainViewModelImpl", "retrieveApartmentInfo fail", t)
            }
        }
    }
}