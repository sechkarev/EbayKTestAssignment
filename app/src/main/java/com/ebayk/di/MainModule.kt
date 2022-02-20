package com.ebayk.di

import com.ebayk.remote.Endpoints
import com.ebayk.usecase.RetrieveApartmentInfo
import com.ebayk.viewmodel.MainViewModel
import com.ebayk.viewmodel.MainViewModelImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val MainModule = module {

    factory<Endpoints> {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://gateway.ebay-kleinanzeigen.de/mobile-api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(Endpoints::class.java)
    }

    factory {
        RetrieveApartmentInfo(get())
    }

    viewModel {
        MainViewModelImpl(get())
    }
}