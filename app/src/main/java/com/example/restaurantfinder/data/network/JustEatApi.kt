package com.example.restaurantfinder.data.network

import com.example.restaurantfinder.data.model.RestaurantResponse
import retrofit2.Response
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
}
