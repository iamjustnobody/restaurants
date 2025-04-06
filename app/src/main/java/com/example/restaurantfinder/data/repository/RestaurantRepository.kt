package com.example.restaurantfinder.data.repository

import com.example.restaurantfinder.data.model.Restaurant
import com.example.restaurantfinder.data.network.JustEatApi
import com.example.restaurantfinder.data.model.RestaurantResponse
import retrofit2.Response

class RestaurantRepository(private val api: JustEatApi) {

//    suspend fun getRestaurantsByPostcode(postcode: String): Response<RestaurantResponse> {
//        return api.getRestaurantsByPostcode(postcode)
//    }
suspend fun getRestaurantsByPostcode(postcode: String): List<Restaurant> {
    return api.getRestaurantsByPostcode(postcode).restaurants // Replace with actual API call logic
}
}
