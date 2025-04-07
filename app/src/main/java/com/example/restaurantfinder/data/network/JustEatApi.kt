package com.example.restaurantfinder.data.network

import com.example.restaurantfinder.data.model.RestaurantResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

//interface JustEatApi {
//    @GET("discovery/uk/restaurants/enriched/bypostcode/{postcode}")
//    suspend fun getRestaurantsByPostcode(@Path("postcode") postcode: String): Response<RestaurantResponse>
//}



interface JustEatApi {

    @GET("discovery/uk/restaurants/enriched/bypostcode/{postcode}")
    suspend fun getRestaurantsByPostcode(
        @Path("postcode") postcode: String
    ): RestaurantResponse

    companion object {
        val service: JustEatApi by lazy {
//            Retrofit.Builder()
//                .baseUrl("https://uk.api.just-eat.io/")
//                .addConverterFactory(MoshiConverterFactory.create())
//                .build()
//                .create(JustEatApi::class.java)
            RetrofitInstance.api
        }
    }
}
