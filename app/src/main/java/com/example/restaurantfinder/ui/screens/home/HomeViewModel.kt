package com.example.restaurantfinder.ui.screens.home


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

import androidx.compose.runtime.State

import androidx.lifecycle.viewModelScope
import com.example.restaurantfinder.data.model.Restaurant
import com.example.restaurantfinder.data.network.RetrofitInstance
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
//    var postcode = mutableStateOf("")
private val _postcode = mutableStateOf("")
    val postcode: State<String> = _postcode
    fun onPostcodeChange(newPostcode: String) {
//        postcode.value = newPostcode
        _postcode.value = newPostcode
    }

    fun searchRestaurants() {
        println("Searching for: ${postcode.value}")
    }

//    var restaurants: List<Restaurant> = emptyList()
//    var isLoading = false
//
//    fun searchRestaurants(postcode: String) {
//        isLoading = true
//        viewModelScope.launch {
//            try {
//                restaurants = RetrofitInstance.api.getRestaurantsByPostcode(postcode)
//                isLoading = false
//            } catch (e: Exception) {
//                // Handle errors (e.g., no internet)
//                isLoading = false
//            }
//        }
//    }
}
