package com.example.restaurantfinder.ui.screens.home



import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.example.restaurantfinder.data.repository.RestaurantRepository
import com.example.restaurantfinder.data.model.Restaurant

//class HomeViewModel(private val restaurantRepository: RestaurantRepository) : ViewModel() {
class HomeViewModel : ViewModel() {
    var restaurants: List<Restaurant> by mutableStateOf(emptyList())
    var isLoading: Boolean by mutableStateOf(false)
    var errorMessage = ""
    fun searchRestaurants(postcode: String) {
        viewModelScope.launch {
            isLoading = true
            try {
                // Call the repository to fetch restaurant data based on postcode
//                restaurants = restaurantRepository.getRestaurantsByPostcode(postcode)
            } catch (e: Exception) {
                errorMessage = "Failed to fetch restaurants."
                // Handle errors (e.g., show snackbar with error message)
            } finally {
                isLoading = false
            }
        }
    }
}
