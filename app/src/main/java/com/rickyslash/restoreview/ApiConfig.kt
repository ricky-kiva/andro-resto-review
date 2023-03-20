package com.rickyslash.restoreview

import androidx.viewbinding.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// 'class' that 'organize' 'API config'
class ApiConfig {
    companion object {
        fun getApiService(): ApiService {
            // making 'logging interceptor' to 'debug response status'
            // this 'if statement' so 'response logging' 'could only be accessed' on 'debug'
            val loggingInterceptor = if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                } else {
                    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
            }
            val client = OkHttpClient.Builder() // make instance of OkHttpClient
                .addInterceptor(loggingInterceptor) // adding 'network interceptor' (loggingInterceptor)
                .build()
            // setting up retrofit
            val retrofit = Retrofit.Builder()
                .baseUrl("https://restaurant-api.dicoding.dev/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            // make implementation of 'ApiService' interface with 'retrofit' configuration
            return retrofit.create(ApiService::class.java)
        }
    }
}