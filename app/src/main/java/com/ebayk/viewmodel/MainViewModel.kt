package com.ebayk.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ebayk.dto.ApartmentDetails
import com.ebayk.usecase.RetrieveApartmentInfo
import com.ebayk.util.ApartmentInfoLoadingStatus
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
                    mapApartmentDetails(
                        retrieveApartmentInfo()
                    )
                )
            } catch (t: Throwable) {
                Log.e("MainViewModelImpl", "retrieveApartmentInfo fail", t)
                apartmentInfo.value = ApartmentInfoLoadingStatus.Error
            }
        }
    }

    private fun mapApartmentDetails(apartmentDetails: ApartmentDetails) = apartmentDetails.copy(
        pictures = apartmentDetails.pictures.map { pictureUrl ->
            pictureUrl.replace("{imageId}", "1")
            //todo: photos not always exist: https://i.ebayimg.com/00/s/MTA2NlgxNjAw/z/w4sAAOSwYRJhSgCD/$_1.JPG is empty, https://i.ebayimg.com/00/s/MTA2NlgxNjAw/z/w4sAAOSwYRJhSgCD/$_57.JPG is not
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
    // todo: delete this mapping. make these fields properties of the classes. for images - both 1 and 57
}