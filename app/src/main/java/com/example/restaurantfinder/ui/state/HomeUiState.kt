package com.example.restaurantfinder.ui.screens.home
import com.example.restaurantfinder.data.model.Restaurant
data class HomeUiState(
    val isLoading: Boolean = false,
    val restaurants: List<Restaurant> = emptyList(),
    val errorMessage: String? = null,
    val snackbarMessage: String? = null,
    val showSuccessDialog: Boolean = false,
    val isLoadingMore: Boolean = false,
    val noMoreItems: Boolean = false
)
