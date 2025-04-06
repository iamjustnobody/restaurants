package com.example.restaurantfinder.data.network

import com.example.restaurantfinder.data.model.Restaurant
import retrofit2.http.GET
import retrofit2.http.Path

interface JustEatApi {

    @GET("discovery/uk/restaurants/enriched/bypostcode/{postcode}")
    suspend fun getRestaurantsByPostcode(@Path("postcode") postcode: String): List<Restaurant>
}
