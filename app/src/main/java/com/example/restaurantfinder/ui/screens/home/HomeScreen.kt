package com.example.restaurantfinder.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(viewModel: HomeViewModel = viewModel()) {
//fun HomeScreen() {
//    var postcode by remember { mutableStateOf("") }
//
//    Column(modifier = Modifier
//        .fillMaxSize()
//        .padding(16.dp)) {
//        OutlinedTextField(
//            value = postcode,
//            onValueChange = { postcode = it },
//            label = { Text("Enter UK postcode") },
//            modifier = Modifier.fillMaxWidth()
//        )
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        Button(
//            onClick = {  },
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text("Search")
//        }
//    }

    val postcode by viewModel.postcode
    val onPostcodeChange = viewModel::onPostcodeChange

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            OutlinedTextField(
                value = postcode,
                onValueChange = onPostcodeChange,
                label = { Text("Enter UK postcode") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    viewModel.searchRestaurants()
                    coroutineScope.launch {
//                        snackbarHostState.showSnackbar("Snackbar Searching for: $postcode")
                        snackbarHostState.showSnackbar(
                            message = "Snackbar Searching for: $postcode",
                            duration = SnackbarDuration.Short
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Search")
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text("Results will go here...")
        }
    }
}
