package com.example.restaurantfinder.data.repository

import com.example.restaurantfinder.data.model.Restaurant
import com.example.restaurantfinder.data.network.JustEatApi
import com.example.restaurantfinder.data.model.RestaurantResponse
import com.example.restaurantfinder.data.network.RetrofitInstance
import retrofit2.Response

class RestaurantRepository(private val api: JustEatApi) {


    suspend fun getRestaurantsByPostcode(postcode: String): List<Restaurant> { //: Response<RestaurantResponse>
        return try {
            api.getRestaurantsByPostcode(postcode).restaurants
        } catch (e: Exception) {
            emptyList()
        }
    }
}

//class RestaurantRepository {
//
//    private val api = JustEatApi.service//RetrofitInstance.api
//
//    suspend fun getRestaurantsByPostcode(postcode: String): List<Restaurant> {
//        val response = api.getRestaurantsByPostcode(postcode)
//        return response.restaurants
//    }
//}
