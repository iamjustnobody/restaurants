package com.example.restaurantfinder.ui.screens.home



import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.example.restaurantfinder.data.repository.RestaurantRepository
import com.example.restaurantfinder.data.model.Restaurant
import com.example.restaurantfinder.data.network.JustEatApi

//class HomeViewModel(private val restaurantRepository: RestaurantRepository) : ViewModel() {
class HomeViewModel : ViewModel() {
//    var restaurants: List<Restaurant> by mutableStateOf(emptyList())
//    var isLoading: Boolean by mutableStateOf(false)
//    var errorMessage = ""
//    fun searchRestaurants(postcode: String) {
//        viewModelScope.launch {
//            isLoading = true
//            try {
//                // Call the repository to fetch restaurant data based on postcode
////                restaurants = restaurantRepository.getRestaurantsByPostcode(postcode)
//            } catch (e: Exception) {
//                errorMessage = "Failed to fetch restaurants."
//                // Handle errors (e.g., show snackbar with error message)
//            } finally {
//                isLoading = false
//            }
//        }
//    }

//

    // Internal mutable state
    private val _uiState = mutableStateOf(HomeUiState())

    // Public read-only state
    val uiState: State<HomeUiState> = _uiState

    fun searchRestaurants(postcode: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

            try {
                val response = JustEatApi.service.getRestaurantsByPostcode(postcode)
                _uiState.value = _uiState.value.copy(
                    restaurants = response.restaurants,
                    isLoading = false
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = e.localizedMessage ?: "Unknown error"
                )
            }
        }
    }
    fun setLoading(loading: Boolean) {
        _uiState.value = _uiState.value.copy(isLoading = loading)
    }
}

