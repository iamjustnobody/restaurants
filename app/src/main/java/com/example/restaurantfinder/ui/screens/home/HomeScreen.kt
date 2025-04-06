package com.example.restaurantfinder.ui.screens.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.restaurantfinder.ui.theme.MyFirstApplicationTheme
import kotlinx.coroutines.launch
import com.example.restaurantfinder.ui.screens.home.HomeViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel = viewModel()) {
    var postcode by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val postcodeRegex = "^[A-Z]{1,2}\\d[A-Z\\d]? \\d[A-Z]{2}$".toRegex()

    // Handle the Clear Button
    fun clearPostcode() {
        postcode = ""
        errorMessage = ""
    }

    // Handle Submit Button
    fun submitPostcode() {
        if (postcode.isEmpty()) {
            errorMessage = "Postcode cannot be empty."
        } else if (!postcodeRegex.matches(postcode)) {
            errorMessage = "Invalid postcode format."
        } else {
            errorMessage = ""
            isLoading = true
            // Simulate API call or use viewModel to fetch restaurants
            // viewModel.searchRestaurants(postcode)
            coroutineScope.launch {
                snackbarHostState.showSnackbar(
                    message = "Searching for restaurants at: $postcode",
                    duration = SnackbarDuration.Short
                )
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Postcode input field using OutlinedTextField
        OutlinedTextField(
            value = postcode,
            onValueChange = { postcode = it },
            label = { Text("Enter UK postcode") },
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
                if (postcode.isNotEmpty()) {
                    IconButton(onClick = { clearPostcode() }) {
                        Text("X")
                    }
                }
            },
            isError = errorMessage.isNotEmpty(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            )
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
                // Uncomment to trigger search in viewModel
                // viewModel.searchRestaurants(postcode)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Search")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Loading Indicator
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp))
        }

        // Snackbar Host
        SnackbarHost(hostState = snackbarHostState)
    }
}
//CircularProgressIndicator replaced by ... indicator inside search button
//when click 'x' to clear the entries, we stop firing api or wont allow to x or change input string
//whole page covered
//skelton placeholder

