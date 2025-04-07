package com.example.restaurantfinder.data.network


import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://uk.api.just-eat.io/"

    val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())  // Add the Kotlin adapter for Kotlin data classes
        .build()

    val api: JustEatApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi))  // Pass custom Moshi instance
            .build()
            .create(JustEatApi::class.java)
    }
}
