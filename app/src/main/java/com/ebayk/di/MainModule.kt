package com.ebayk.di

import com.ebayk.remote.Endpoints
import com.ebayk.usecase.RetrieveApartmentInfo
import com.ebayk.viewmodel.MainViewModel
import okhttp3.Credentials
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val MainModule = module {

    factory<Endpoints> {
        val credentials = Credentials.basic("candidate", "yx6Xz62y")

        val retrofit = Retrofit.Builder()
            .baseUrl("https://gateway.ebay-kleinanzeigen.de/mobile-api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient
                .Builder()
                .addInterceptor(
                    HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    }
                )
                .addInterceptor {
                    it.proceed(
                        it.request()
                            .newBuilder()
                            .addHeader("authorization", credentials)
                            .build()
                    )
                }
                .build()
            )
            .build()

        retrofit.create(Endpoints::class.java)
    }

    factory {
        RetrieveApartmentInfo(get())
    }

    viewModel {
        MainViewModel(get())
    }
}