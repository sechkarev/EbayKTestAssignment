package com.ebayk.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ebayk.dto.ApartmentDetails
import com.ebayk.usecase.RetrieveApartmentInfo
import kotlinx.coroutines.launch
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class MainViewModel(
    retrieveApartmentInfo: RetrieveApartmentInfo
) : ViewModel() {

    val apartmentInfo = MutableLiveData<ApartmentDetails?>()

    init {
        viewModelScope.launch {
            try {
                apartmentInfo.value = mapApartmentDetails(retrieveApartmentInfo())
            } catch (t: Throwable) {
                Log.e("MainViewModelImpl", "retrieveApartmentInfo fail", t)
            }
        }
    }

    private fun mapApartmentDetails(apartmentDetails: ApartmentDetails) = apartmentDetails.copy(
        pictures = apartmentDetails.pictures.map { pictureUrl ->
            pictureUrl.replace("{imageId}", "1") //todo: photos not always exist
        },
        postDate = apartmentDetails.postDate.substringBefore("T").let {
            try {
                SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(it)?.let { date ->
                    SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(date)
                } ?: "-"
            } catch (parseException: ParseException) {
                Log.e("MainViewModelImpl", "failed to parse date $it", parseException)
                "-"
            }
        }
    )
}