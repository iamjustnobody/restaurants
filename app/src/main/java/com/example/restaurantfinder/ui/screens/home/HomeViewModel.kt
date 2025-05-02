package com.example.restaurantfinder.ui.screens.home



import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.restaurantfinder.data.model.FilterOptions
import kotlinx.coroutines.launch
import com.example.restaurantfinder.data.repository.RestaurantRepository
import com.example.restaurantfinder.data.model.Restaurant
import com.example.restaurantfinder.data.network.JustEatApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: RestaurantRepository) : ViewModel() {


    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

//    private val _isFilterDialogVisible = MutableStateFlow(false)
//    val isFilterDialogVisible: StateFlow<Boolean> = _isFilterDialogVisible.asStateFlow()
//
//    private val _filterOptions = MutableStateFlow(FilterOptions())
//    val filterOptions: StateFlow<FilterOptions> = _filterOptions.asStateFlow()
//
//    private val _filteredRestaurants = MutableStateFlow<List<Restaurant>>(emptyList())
//    val filteredRestaurants: StateFlow<List<Restaurant>> = _filteredRestaurants.asStateFlow()

//    private val repository = RestaurantRepository(JustEatApi.service)
    fun searchRestaurants(postcode: String, initial: Boolean = true) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    snackbarMessage = if (initial) "Searching for restaurants at: $postcode" else null,
                    errorMessage = null,
                    isLoading = initial,
                    isLoadingMore = !initial,
                    showSuccessDialog = false
                )
            }

            try {
                val restaurants = repository.getRestaurantsByPostcode(postcode)

                _uiState.update {
                    it.copy(
                        restaurants = if (initial) restaurants else it.restaurants + restaurants,
                        filteredRestaurants = restaurants, // or apply filters if needed
                        snackbarMessage = when {
                            initial && restaurants.isEmpty() -> "No restaurants found."
                            !initial && restaurants.isEmpty() -> "No more restaurants found."
                            else -> "Restaurants loaded successfully 🎉"
                        },
                        isLoading = false,
                        isLoadingMore = false,
                        showSuccessDialog = restaurants.isNotEmpty(),//initial,
                        noMoreItems = restaurants.isEmpty()
                    )
                }

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        isLoadingMore = false,
                        errorMessage = e.localizedMessage ?: "Unknown error",
                        snackbarMessage = "Error: ${e.localizedMessage ?: "Unknown error"}",
                        showSuccessDialog = false
                    )
                }
            }
        }
    }

    fun fetchInitialRestaurants(postcode: String) {
        searchRestaurants(postcode, initial = true)
    }

    fun loadMoreRestaurants(postcode: String) {
        searchRestaurants(postcode, initial = false)
    }

    fun setSnackbarMessage(message: String?) {
        _uiState.update { it.copy(snackbarMessage = message) }
    }

    fun dismissSuccessDialog() {
        _uiState.update { it.copy(showSuccessDialog = false) }
    }


    fun getFilteredRestaurants(restaurants: List<Restaurant>):List<Restaurant> { //:Unit
        val filters = _uiState.value.filterOptions
        val filtered = restaurants.filter { restaurant ->
            (!filters.isNew || restaurant.isNew == true) &&
                    (!filters.isCollection || restaurant.isCollection == true) &&
                    (!filters.isDelivery || restaurant.isDelivery == true) &&
                    (!filters.isOpenNowForCollection || restaurant.isOpenNowForCollection == true) &&
                    (!filters.isOpenNowForDelivery || restaurant.isOpenNowForDelivery == true) &&
                    (!filters.isOpenNowForPreorder || restaurant.isOpenNowForPreorder == true) &&
                    (!filters.deliveryIsOpen || restaurant.availability?.delivery?.isOpen == true) &&
                    (!filters.deliveryCanPreOrder || restaurant.availability?.delivery?.canPreOrder == true)
        }

//        _uiState.update { it.copy(filteredRestaurants = filtered) }

        return filtered
    }
    fun applyFilters():Unit {
        val allRestaurants = _uiState.value.restaurants
        val filtered = getFilteredRestaurants(allRestaurants)
        _uiState.update { it.copy(filteredRestaurants = filtered) }
    }

    fun updateFilterDialogVisible(visible: Boolean) {
        _uiState.update { it.copy(isFilterDialogVisible = visible) }
    }

    fun updateFilterOptions(options: FilterOptions) {
        _uiState.update { it.copy(filterOptions = options) }
    }

    fun filterAndSortRestaurants(options: FilterOptions, isVisible: Boolean) {
        val filtered = getFilteredRestaurants(_uiState.value.restaurants)
        val sortedFiltered = getSortedRestaurnts(filtered, _uiState.value.sortingOption)
        _uiState.update { it.copy( filteredRestaurants = sortedFiltered, filterOptions = options, isFilterDialogVisible = isVisible) }
    }

    fun setRestaurants(restaurants: List<Restaurant>) {
        _uiState.update {
            it.copy(
                restaurants = restaurants,
                filteredRestaurants = restaurants
            )
        }
    }

    fun getSortedRestaurnts(restaurants: List<Restaurant>, option: SortingOption) :List<Restaurant> {
        val sorted = when (option) {
            SortingOption.NAME_ASC -> _uiState.value.filteredRestaurants.sortedBy { it.name }
            SortingOption.NAME_DESC -> _uiState.value.filteredRestaurants.sortedByDescending { it.name }
            SortingOption.RATING_ASC -> _uiState.value.filteredRestaurants.sortedBy { it.rating?.starRating }
            SortingOption.RATING_DESC -> _uiState.value.filteredRestaurants.sortedByDescending { it.rating?.starRating }
            SortingOption.CUISINE_ASC -> _uiState.value.filteredRestaurants.sortedBy {
                it.cuisines.joinToString(", ") { cuisine -> cuisine.name }
            }
            SortingOption.CUISINE_DESC -> _uiState.value.filteredRestaurants.sortedByDescending {
                it.cuisines.joinToString(", ") { cuisine -> cuisine.name }
            }
            SortingOption.DEFAULT -> _uiState.value.filteredRestaurants
        }
        return sorted;
    }
    fun sortFilteredRestaurants(option: SortingOption) {
        val sorted = getSortedRestaurnts(_uiState.value.filteredRestaurants, option)

        _uiState.update {
            it.copy(filteredRestaurants = sorted, sortingOption = option)
        }
    }

    fun toggleFilterDialog(show: Boolean) {
//        _isFilterDialogVisible.value = show
        _uiState.update { it.copy(isFilterDialogVisible = show) }
    }
}

