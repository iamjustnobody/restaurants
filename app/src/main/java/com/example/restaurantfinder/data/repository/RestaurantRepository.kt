package com.example.restaurantfinder.data.repository

import android.util.Log
import com.example.restaurantfinder.data.model.Restaurant
import com.example.restaurantfinder.data.network.JustEatApi
import com.example.restaurantfinder.data.model.RestaurantResponse
import com.example.restaurantfinder.data.network.RetrofitInstance
import retrofit2.Response
import javax.inject.Inject


interface RestaurantRepositoryBase {
    suspend fun getRestaurantsByPostcode(postcode: String): List<Restaurant>
}

class RestaurantRepository @Inject constructor(private val api: JustEatApi) : RestaurantRepositoryBase {
    init {
        Log.d("DI", "RestaurantRepository injected: $this")
    }
    override suspend fun getRestaurantsByPostcode(postcode: String): List<Restaurant> {
        return try {
            api.getRestaurantsByPostcode(postcode).restaurants
        } catch (e: Exception) {
            emptyList()
        }
    }
}

//class RestaurantRepository @Inject constructor(private val api: JustEatApi) {
//
//
//    suspend fun getRestaurantsByPostcode(postcode: String): List<Restaurant> { //: Response<RestaurantResponse>
//        return try {
//            api.getRestaurantsByPostcode(postcode).restaurants
//        } catch (e: Exception) {
//            emptyList()
//        }
//    }
//}

//class RestaurantRepository {
//
//    private val api = JustEatApi.service//RetrofitInstance.api
//
//    suspend fun getRestaurantsByPostcode(postcode: String): List<Restaurant> {
//        val response = api.getRestaurantsByPostcode(postcode)
//        return response.restaurants
//    }
//}
