package com.catchmate.data.datasource.remote

import com.catchmate.data.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class RetrofitClient
    @Inject
    constructor() {
        val retrofit: Retrofit by lazy {
            Retrofit
                .Builder()
                .baseUrl(BuildConfig.SERVER_DOMAIN)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        inline fun <reified T> createApi(): T = retrofit.create(T::class.java)
    }
