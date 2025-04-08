package com.example.restaurantfinder.ui.screens.home

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.border
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.restaurantfinder.ui.theme.MyFirstApplicationTheme
import kotlinx.coroutines.launch
import com.example.restaurantfinder.ui.screens.home.HomeViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.restaurantfinder.ui.components.RestaurantCard
import com.example.restaurantfinder.ui.components.SkeletonCard
import com.example.restaurantfinder.ui.components.SortModal


//import androidx.compose.foundation.placeholder.placeholder
//import androidx.compose.foundation.placeholder.PlaceholderHighlight
//import androidx.compose.foundation.shimmer.shimmer

import com.google.accompanist.placeholder.material.placeholder
//import com.google.accompanist.placeholder.material.PlaceholderHighlight
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.shimmer
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel = viewModel()) {
    var postcode by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
//    var isLoading by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
//    val state = viewModel.uiState

    val state by viewModel.uiState
    // Function to show snackbar messages
    fun showSnackbar(message: String) {
        coroutineScope.launch {
            // Dismiss the current snackbar if it's being displayed
            snackbarHostState.currentSnackbarData?.dismiss()
            snackbarHostState.showSnackbar(
                message = message,
                duration = SnackbarDuration.Short
            )
        }
    }

    val focusManager = LocalFocusManager.current

//    val postcodeRegex = "^[A-Z]{1,2}\\d[A-Z\\d]? \\d[A-Z]{2}$".toRegex()
//    val postcodeRegex = "^(?i)^[A-Z]{1,2}\\d[A-Z\\d]? \\d[A-Z]{2}$".toRegex()

//    val postcodeRegex = "^[A-Z]{1,2}\\d[A-Z\\d]? \\d[A-Z]{2}$".toRegex(RegexOption.IGNORE_CASE)
    val postcodeRegex = "^[A-Za-z]{1,2}\\d[A-Za-z\\d]? \\d[A-Za-z]{2}$".toRegex()


    val isValidPostcode = !postcode.isEmpty() && postcodeRegex.matches(postcode)

    // Handle the Clear Button
    fun clearPostcode() {
        postcode = ""
        errorMessage = ""
    }

    // LazyListState for scroll detection
    val listState = rememberLazyListState()
    val showBackToTop = remember { derivedStateOf { listState.firstVisibleItemIndex > 2 } }

    // Trigger API call when scrolled to the end of the list
//    if (listState.isScrolledToEnd()) {
////        viewModel.searchRestaurants(postcode)
//        viewModel.loadMoreRestaurants(postcode)
//    }//ok
//    LaunchedEffect(listState, state.restaurants) {
//        snapshotFlow { listState.layoutInfo }
//            .collect { layoutInfo ->
//                val lastVisible = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
//                val totalItems = layoutInfo.totalItemsCount
//
//                // Guard against empty list & already loading
//                if (totalItems > 0 &&
//                    lastVisible >= totalItems - 1 &&
//                    !state.isLoadingMore &&
//                    !state.isLoading && !state.noMoreItems && isValidPostcode
//                ) {
////                    viewModel.loadMoreRestaurants(postcode)
//                }
//            }
//    }//ok for load more (but need to be careful about first initial loading of the site while no postcode input


    // 🔔 Show Snackbar when message is set
    LaunchedEffect(state.snackbarMessage) {
        state.snackbarMessage?.let { message ->
//            snackbarHostState.showSnackbar(message)
            snackbarHostState.showSnackbar(
                message = message,
//                duration = SnackbarDuration.Short // ≈ 4 seconds
                duration = if (message.contains("success", ignoreCase = true)) {
                    SnackbarDuration.Short // shorter ~2-3 seconds
                } else {
                    SnackbarDuration.Long // error gets more time
                }
            )
            viewModel.setSnackbarMessage(null) // Clean up
        }
    }

    // 🎉 Show Success Dialog when restaurants loaded
    if (state.showSuccessDialog) {
        LaunchedEffect(Unit) {
            delay(2500) //2.5 secs
            viewModel.dismissSuccessDialog()
        }
        AlertDialog(
            onDismissRequest = { viewModel.dismissSuccessDialog() },
            title = { Text("Success!") },
            text = { Text("Restaurants loaded successfully.") },
            confirmButton = {
                TextButton(onClick = { viewModel.dismissSuccessDialog() }) {
                    Text("OK")
                }
            }
        )
    }

    // Handle Submit Button
    fun submitPostcode() {
        if (state.isLoading) return
        if (postcode.isEmpty()) {
            errorMessage = "Postcode cannot be empty."
        } else if (!postcodeRegex.matches(postcode)) {
            errorMessage = "Invalid postcode format."
        } else {
            errorMessage = ""
//            isLoading = true
            // Simulate API call or use viewModel to fetch restaurants
            // viewModel.searchRestaurants(postcode)
//            coroutineScope.launch {
//                snackbarHostState.showSnackbar(
//                    message = "Searching for restaurants at: $postcode",
//                    duration = SnackbarDuration.Short,
//                )
//            }
            coroutineScope.launch {
//                viewModel.setLoading(true) // 🔥
////                showSnackbar("Searching for restaurants at: $postcode") //ok
//                // Show snackbar
//                val snackbar = snackbarHostState.showSnackbar(
//                    message = "Searching for restaurants at: $postcode",
//                    duration = SnackbarDuration.Indefinite
//                )
//                // Simulate API delay
////                    delay(8000)
////                isLoading = false
//                // Handle API response
//                viewModel.searchRestaurants(postcode)
//
//                // 🔥 Wait for isLoading to become false
//                snapshotFlow { state.isLoading }
//                    .filter { !it }
//                    .first()  // suspend until loading is done
//
//                // Dismiss snackbar manually (if still showing)
//                snackbarHostState.currentSnackbarData?.dismiss()

                viewModel.fetchInitialRestaurants(postcode)//viewModel.searchRestaurants(postcode)

                focusManager.clearFocus()
            }
        }
    }

////    val restaurants by viewModel.filteredRestaurants.observeAsState(emptyList())
////    var showModal by remember { mutableStateOf(false) }
//    val restaurants = viewModel.filteredRestaurants
////    var selectedSortingOption by remember { mutableStateOf(SortingOption.NAME) }
//
//    val sortingOptions = SortingOption.values()
//
//    var showModal = remember { mutableStateOf(false) }
//    val selectedSortingOption = remember { mutableStateOf(SortingOption.DEFAULT) }


// UI components
    Scaffold(
//        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Restaurant Finder") }
                // Add other TopAppBar properties if needed
            )
        },
        bottomBar = {
            BottomAppBar {
                // Add BottomAppBar content if needed
            }
        })
    //content =
    { paddingValues -> Box(modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues) // keep this to respect top/bottom bars
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
//            .padding(paddingValues)
//            .padding(16.dp),
                .padding(horizontal = 16.dp), // only horizontal padding
//                .padding(top = paddingValues.calculateTopPadding()), // just top padding from Scaffold
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
//        Spacer(modifier = Modifier.height(8.dp))
            // Postcode input field using OutlinedTextField
            OutlinedTextField(
                value = postcode,
                onValueChange = { postcode = it; errorMessage = "" },
                label = { Text("Enter UK postcode") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    unfocusedIndicatorColor = Color.Gray,
                    errorIndicatorColor = MaterialTheme.colorScheme.error
                ),
//            shape = RoundedCornerShape(12.dp),
                trailingIcon = {
                    if (postcode.isNotEmpty() && !state.isLoading) {
                        IconButton(onClick = { clearPostcode() }) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = "Clear postcode",
                                tint = Color.Black,
                            )
                        }
                    }
                },
                isError = errorMessage.isNotEmpty(),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                        submitPostcode()
                    }
                ),
                enabled = !state.isLoading

            )

            Spacer(modifier = Modifier.height(8.dp))

            // Error message
            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Search Button
            Button(
                onClick = {
                    submitPostcode()
                    // trigger search in viewModel
//                 viewModel.searchRestaurants(postcode)
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !state.isLoading
            ) {
                if (state.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(24.dp)
                            .padding(end = 8.dp),
                        color = MaterialTheme.colorScheme.onPrimary,
                        strokeWidth = 2.dp
                    )
                }
                Text("Search")
            }

            Spacer(modifier = Modifier.height(16.dp))
            // Sorting Button
//            Button(
//                onClick = { showModal.value = true },
//                modifier = Modifier
//                    .padding(16.dp)
//                    .fillMaxWidth()
//            ) {
//                Text("Sort by: ${selectedSortingOption.value.name}")
//            }
//            Spacer(modifier = Modifier.height(16.dp))

            // Loading Indicator
            if (state.isLoading) {
//            CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp))
//            Column {
//                repeat(3) {
//                    Box(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(100.dp)
//                            .padding(vertical = 4.dp)
//                            .background(Color.LightGray.copy(alpha = 0.5f))
//                    )
//                }
//            }
                //below 5 cards ok
//                repeat(5) {
//                    Card(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(8.dp)
//                            .height(100.dp)
//                            .placeholder(
//                                visible = true,
//                                highlight = PlaceholderHighlight.shimmer()
//                            )
//                    ) {}
//                }//ok
                repeat(10) {
                    SkeletonCard()
                }
            } else if (state.errorMessage != null) {
                Text("Error: ${state.errorMessage}", color = Color.Red)
            } else {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                ) {
//                    LazyColumn{
//                        items(state.restaurants.take(10)) { restaurant ->
//                            RestaurantCard(restaurant)
//                        }
//                    }//ok
                    LazyColumn(state = listState){
                        items(state.restaurants.take(10)) { restaurant ->
                            RestaurantCard(restaurant = restaurant)
                        }
//                        if (state.isLoadingMore) {
//                            item {
//                                Box(
//                                    modifier = Modifier
//                                        .fillMaxWidth()
//                                        .padding(16.dp),
//                                    contentAlignment = Alignment.Center
//                                ) {
//                                    CircularProgressIndicator()
//                                }
//                            }
//                        }//ok
                        // 👇 Place loading or "no more items" here (next section)
//                        item {
//                            when {
//                                state.isLoadingMore -> {
//                                    Row(
//                                        modifier = Modifier
//                                            .fillMaxWidth()
//                                            .padding(16.dp),
//                                        horizontalArrangement = Arrangement.Center
//                                    ) {
//                                        CircularProgressIndicator()
//                                    }
//                                }
//
//                                state.noMoreItems -> {
//                                    Row(
//                                        modifier = Modifier
//                                            .fillMaxWidth()
//                                            .padding(16.dp),
//                                        horizontalArrangement = Arrangement.Center
//                                    ) {
//                                        Text("No more restaurants to load")
//                                    }
//                                }
//                            }
//                        }//ok for load more
                    }//ok
//                    LazyColumn(state = listState) {
//                        items(state.restaurants) { restaurant ->
//                            // SwipeToDismiss for each restaurant item
//                            SwipeToDismiss(
//                                state = rememberDismissState(),
//                                background = {
//                                    Box(
//                                        modifier = Modifier.fillMaxSize().background(Color.Gray)
//                                    ) {
//                                        Icon(Icons.Default.Favorite, contentDescription = "Favorite", modifier = Modifier.align(Alignment.CenterEnd))
//                                    }
//                                },
//                                dismissContent = {
//                                    RestaurantCard(restaurant = restaurant)
//                                }
//                            )
//                        }
//                    }//ok

//                    LazyVerticalGrid(
//                        cells = GridCells.Fixed(2), // Two items per row
//                        content = {
//                            items(state.restaurants.take(10)) { restaurant ->
//                                RestaurantCard(restaurant)
//                            }
//                        }
//                    )

                    // 👇 FAB to scroll to top
                    if (showBackToTop.value) {
                        FloatingActionButton(
                            onClick = {
                                coroutineScope.launch {
                                    listState.animateScrollToItem(0)
                                }
                            },
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .padding(16.dp)
                        ) {
                            Icon(Icons.Default.KeyboardArrowUp, contentDescription = "Back to Top")
                        }
                    }
                }
            }

            // Snackbar Host
//        SnackbarHost(hostState = snackbarHostState)
        }

        // ✅ Top-positioned Snackbar
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
//                .align(Alignment.TopCenter)
//                .padding(top = 120.dp) // below TopAppBar
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp) // Optional padding to create space from the bottom
        ) { data ->
            val isError = data.visuals.message.startsWith("Error", ignoreCase = true)
            val isSuccess = data.visuals.message.contains("success", ignoreCase = true)

            Snackbar(
                snackbarData = data,
                containerColor = when {
                    isError -> MaterialTheme.colorScheme.error
                    isSuccess -> Color(0xFF4CAF50)
                    else -> MaterialTheme.colorScheme.secondary
                },
                contentColor = Color.White,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            )
        }


//        SortModal(
//            showModal = showModal,
//            selectedSortingOption = selectedSortingOption,
//            sortingOptions = sortingOptions, // Converting list to array
//            viewModel = viewModel
//        )

    }
    }//)
}

// Extension function for LazyListState to detect end of the list
fun LazyListState.isScrolledToEnd(): Boolean {
    return layoutInfo.visibleItemsInfo.lastOrNull()?.index == layoutInfo.totalItemsCount - 1
}
//CircularProgressIndicator replaced by ... indicator inside search button
//when click 'x' to clear the entries, we stop firing api or wont allow to x or change input string
//whole page covered
//skelton placeholder

enum class SortingOption {
    NAME, RATING, CUISINE, DEFAULT
}