package com.example.restaurantfinder.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen() {
    var postcode by remember { mutableStateOf("") }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        OutlinedTextField(
            value = postcode,
            onValueChange = { postcode = it },
            label = { Text("Enter UK postcode") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { /* TODO: Trigger search */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Search")
        }

        // TODO: Show list of restaurants here
    }
}
