package com.ebayk.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ebayk.usecase.RetrieveApartmentInfo
import kotlinx.coroutines.launch

interface MainViewModel {
    val imageUrls: LiveData<List<String>>
}

class MainViewModelImpl(
    retrieveApartmentInfo: RetrieveApartmentInfo
) : ViewModel(), MainViewModel {

    override val imageUrls = MutableLiveData<List<String>>(
        listOf(
            "https://i.ebayimg.com/00/s/MTA2NlgxNjAw/z/~CoAAOSwjZJhSgCC/\$_57.JPG",
            "https://i.ebayimg.com/00/s/MTA2NlgxNjAw/z/~CoAAOSwjZJhSgCC/\$_57.JPG",
            "https://i.ebayimg.com/00/s/MTA2NlgxNjAw/z/~CoAAOSwjZJhSgCC/\$_57.JPG",
            "https://i.ebayimg.com/00/s/MTA2NlgxNjAw/z/~CoAAOSwjZJhSgCC/\$_57.JPG",
            "https://i.ebayimg.com/00/s/MTA2NlgxNjAw/z/~CoAAOSwjZJhSgCC/\$_57.JPG",
            "https://i.ebayimg.com/00/s/MTA2NlgxNjAw/z/~CoAAOSwjZJhSgCC/\$_57.JPG",
            "https://i.ebayimg.com/00/s/MTA2NlgxNjAw/z/~CoAAOSwjZJhSgCC/\$_57.JPG",
        )
    )

    init {
        viewModelScope.launch {
            try {
                retrieveApartmentInfo()
            } catch (t: Throwable) {
                Log.e("MainViewModelImpl", "retrieveApartmentInfo fail", t)
            }
        }
    }
}