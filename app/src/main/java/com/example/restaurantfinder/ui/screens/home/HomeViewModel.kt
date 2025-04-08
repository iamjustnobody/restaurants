package com.example.restaurantfinder.ui.screens.home



import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restaurantfinder.data.model.FilterOptions
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

    // Filter state
    var isFilterDialogVisible by mutableStateOf(false)
        private set

    var filterOptions by mutableStateOf(FilterOptions())
        private set

    var filteredRestaurants by mutableStateOf<List<Restaurant>>(emptyList())
        private set


    suspend fun searchRestaurants(postcode: String, initial: Boolean = true): List<Restaurant> {
        try {
            // Update UI before the API call
            _uiState.value = _uiState.value.copy(
                snackbarMessage = if (initial) "Searching for restaurants at: $postcode" else null,
                errorMessage = null,
                isLoading = initial,
                isLoadingMore = !initial,
                showSuccessDialog = false
            )

            val response = JustEatApi.service.getRestaurantsByPostcode(postcode)
            val restaurants = response.restaurants
            setRestaurants(restaurants)

            // Post-API call success
            _uiState.value = _uiState.value.copy(
                snackbarMessage = if (initial) {
                    if (restaurants.isEmpty()) "No restaurants found." else "Restaurants loaded successfully 🎉"
                }
                else { if (restaurants.isEmpty()) "No more restaurants found." else null },
                isLoading = false,
                isLoadingMore = false,
                showSuccessDialog = initial,
                noMoreItems = restaurants.isEmpty()
            )

            return restaurants
        } catch (e: Exception) {
            // Post-API call failure
            _uiState.value = _uiState.value.copy(
                isLoading = false,
                isLoadingMore = false,
                errorMessage = e.localizedMessage ?: "Unknown error",
                snackbarMessage = "Error: ${e.localizedMessage ?: "Unknown error"}"
            )
            return emptyList()
        }
    }

    //ok below
//    fun searchRestaurants(postcode: String): List<Restaurant> {
//        val restaurants = mutableListOf<Restaurant>()
//        viewModelScope.launch {
////            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
//            _uiState.value = _uiState.value.copy(
//                isLoading = true,
//                snackbarMessage = "Searching for restaurants at: $postcode",
//                showSuccessDialog = false,
//                errorMessage = null,
//                isLoadingMore = true
//            )
//            try {
//                val response = JustEatApi.service.getRestaurantsByPostcode(postcode)
//                restaurants.addAll(response.restaurants)
//                _uiState.value = _uiState.value.copy(
//                    restaurants = response.restaurants,
//                    isLoading = false,
////                    snackbarMessage = null,
//                    showSuccessDialog = true,
//                    snackbarMessage = "Restaurants loaded successfully 🎉",
////                    showSuccessDialog = false,
//                    isLoadingMore = false,
//                )
//            } catch (e: Exception) {
//                _uiState.value = _uiState.value.copy(
//                    isLoading = false,
//                    errorMessage = e.localizedMessage ?: "Unknown error",
//                    snackbarMessage = "Error: ${e.localizedMessage ?: "Unknown error"}",
//                    isLoadingMore = false,
//                )
//            }
//        }
//        return restaurants
//    }
    fun setLoading(loading: Boolean) {
        _uiState.value = _uiState.value.copy(isLoading = loading)
    }
    fun setSnackbarMessage(message: String?) {
        _uiState.value = _uiState.value.copy(snackbarMessage = message)
    }

    fun dismissSuccessDialog() {
        _uiState.value = _uiState.value.copy(showSuccessDialog = false)
    }

    fun loadMoreRestaurants(postcode: String) {
        viewModelScope.launch {
            val moreRestaurants = searchRestaurants(postcode, initial = false)

            if (moreRestaurants.isEmpty()) {
                _uiState.value = _uiState.value.copy(
                    isLoadingMore = false,
                    noMoreItems = true,
                    snackbarMessage = "You've reached the end of the list!"
                )
            } else {
                _uiState.value = _uiState.value.copy(
                    restaurants = _uiState.value.restaurants + moreRestaurants,
                    isLoadingMore = false
                )
            }
        }
    }

    //ok below
//    fun loadMoreRestaurants(postcode: String) {
//        viewModelScope.launch {
//            // Implement logic to load more restaurants (e.g., API call)
//            // Update the UI state with the new list of restaurants
//            val moreRestaurants = searchRestaurants(postcode) // Replace with actual function
//            _uiState.value = _uiState.value.copy(
//                restaurants = _uiState.value.restaurants + moreRestaurants
//            )
//        }
//    }

    fun fetchInitialRestaurants(postcode: String) {
        viewModelScope.launch {
            val initialRestaurants = searchRestaurants(postcode, initial = true)
            applyFilters(initialRestaurants)
            _uiState.value = _uiState.value.copy(
                restaurants = initialRestaurants,
                noMoreItems = false // Reset for new search
            )
        }
    }



    // Apply filter logic
    fun applyFilters(restaurants: List<Restaurant>): List<Restaurant>  {
        filteredRestaurants = restaurants.filter { restaurant ->
            (!filterOptions.isNew || restaurant.isNew == true) &&
                    (!filterOptions.isCollection || restaurant.isCollection == true) &&
                    (!filterOptions.isDelivery || restaurant.isDelivery == true) &&
                    (!filterOptions.isOpenNowForCollection || restaurant.isOpenNowForCollection == true) &&
                    (!filterOptions.isOpenNowForDelivery || restaurant.isOpenNowForDelivery == true) &&
                    (!filterOptions.isOpenNowForPreorder || restaurant.isOpenNowForPreorder == true) &&
                    (!filterOptions.deliveryIsOpen || restaurant.availability?.delivery?.isOpen == true) &&
                    (!filterOptions.deliveryCanPreOrder || restaurant.availability?.delivery?.canPreOrder == true)
        }
        return filteredRestaurants
    }

    // Set filter dialog visibility
    fun updateFilterDialogVisible(visible: Boolean) {
        isFilterDialogVisible = visible
    }

    fun updateFilterOptions(options: FilterOptions) {
        filterOptions = options
    }

//    fun setFilteredRestaurants(restaurants: List<Restaurant>) {
//        filteredRestaurants = restaurants
//    }
    fun setRestaurants(restaurants: List<Restaurant>) {
        _uiState.value = _uiState.value.copy(
            restaurants = restaurants,
            filteredRestaurants = restaurants // Initially show all restaurants
        )
    }
    fun updateFilteredRestaurants(restaurants: List<Restaurant>) {
        _uiState.value = _uiState.value.copy(filteredRestaurants = restaurants)
    }

    // Add sorting function
    fun sortRestaurants(option: SortingOption) {
        filteredRestaurants = when (option) {
            SortingOption.NAME -> filteredRestaurants.sortedBy { it.name }
            SortingOption.RATING -> filteredRestaurants.sortedByDescending { it.rating?.starRating }
            SortingOption.CUISINE -> filteredRestaurants.sortedBy { it.cuisines.joinToString(", ") { cuisine -> cuisine.name } }
            SortingOption.DEFAULT -> filteredRestaurants
        }
    }
}

