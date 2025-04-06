package com.example.restaurantfinder.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restaurantfinder.data.model.Restaurant
import com.example.restaurantfinder.data.repository.RestaurantRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: RestaurantRepository = RestaurantRepository()
) : ViewModel() {

    private val _restaurants = MutableStateFlow<List<Restaurant>>(emptyList())
    val restaurants: StateFlow<List<Restaurant>> = _restaurants

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _snackbarMessage = MutableStateFlow<String?>(null)
    val snackbarMessage: StateFlow<String?> = _snackbarMessage

    fun searchRestaurants(postcode: String) {
        if (postcode.isBlank()) {
            _snackbarMessage.value = "Please enter a postcode."
            return
        }

        viewModelScope.launch {
            _loading.value = true
            try {
                val response = repository.getRestaurantsByPostcode(postcode)
                val restaurants = response.restaurants.take(10)

                if (restaurants.isEmpty()) {
                    _snackbarMessage.value = "No restaurants found for $postcode"
                }

                _restaurants.value = restaurants
            } catch (e: Exception) {
                _snackbarMessage.value = "Failed to load restaurants."
            } finally {
                _loading.value = false
            }
        }
    }
}
