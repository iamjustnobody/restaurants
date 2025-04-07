package com.example.restaurantfinder.ui.screens.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.border
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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

    val postcodeRegex = "^[A-Z]{1,2}\\d[A-Z\\d]? \\d[A-Z]{2}$".toRegex()

    // Handle the Clear Button
    fun clearPostcode() {
        postcode = ""
        errorMessage = ""
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
                viewModel.setLoading(true) // 🔥
//                showSnackbar("Searching for restaurants at: $postcode") //ok
                // Show snackbar
                val snackbar = snackbarHostState.showSnackbar(
                    message = "Searching for restaurants at: $postcode",
                    duration = SnackbarDuration.Indefinite
                )
                // Simulate API delay
//                    delay(8000)
//                isLoading = false
                // Handle API response
                viewModel.searchRestaurants(postcode)

                // 🔥 Wait for isLoading to become false
                snapshotFlow { state.isLoading }
                    .filter { !it }
                    .first()  // suspend until loading is done

                // Dismiss snackbar manually (if still showing)
                snackbarHostState.currentSnackbarData?.dismiss()

                focusManager.clearFocus()
            }
        }
    }

// UI components
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
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
    { paddingValues -> Column(
        modifier = Modifier
            .fillMaxSize()
//            .padding(paddingValues)
//            .padding(16.dp),
            .padding(horizontal = 16.dp) // only horizontal padding
            .padding(top = paddingValues.calculateTopPadding()), // just top padding from Scaffold
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
                        Text("X")
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
            repeat(5) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .height(100.dp)
                        .placeholder(
                            visible = true,
                            highlight = PlaceholderHighlight.shimmer()
                        )
                ) {}
            }
        } else if (state.errorMessage != null) {
            Text("Error: ${state.errorMessage}", color = Color.Red)
        } else {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                LazyColumn {
                    items(state.restaurants.take(10)) { restaurant ->
                        RestaurantCard(restaurant)
                    }
                }
            }
        }

        // Snackbar Host
//        SnackbarHost(hostState = snackbarHostState)
    }
    }//)
}
//CircularProgressIndicator replaced by ... indicator inside search button
//when click 'x' to clear the entries, we stop firing api or wont allow to x or change input string
//whole page covered
//skelton placeholder

