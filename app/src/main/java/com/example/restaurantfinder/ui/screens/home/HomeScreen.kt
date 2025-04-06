package com.example.restaurantfinder.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.restaurantfinder.ui.components.RestaurantCard

@Composable
fun HomeScreen() {
    val viewModel: HomeViewModel = viewModel()
    var postcode by remember { mutableStateOf("") }
    val restaurants by viewModel.restaurants.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val snackbarMessage by viewModel.snackbarMessage.collectAsState()

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = remember { SnackbarHostState() }) {
                snackbarMessage?.let {
                    Snackbar { Text(it) }
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = postcode,
                onValueChange = { postcode = it },
                label = { Text("Enter UK Postcode") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { viewModel.searchRestaurants(postcode) },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text("Search")
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (loading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else {
                LazyColumn {
                    items(restaurants) { restaurant ->
                        RestaurantCard(restaurant = restaurant)
                    }
                }
            }
        }
    }
}
