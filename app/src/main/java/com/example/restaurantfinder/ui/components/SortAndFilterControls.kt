package com.example.restaurantfinder.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DropdownMenu
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.restaurantfinder.ui.screens.home.SortingOption

@Composable
fun SortAndFilterControls(currentSorting: SortingOption, onSortSelect: (SortingOption) -> Unit, onFilterClick: () -> Unit) {
    var expanded by remember {
        mutableStateOf(false)
    }
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
//        verticalAlignment = Alignment.CenterVertically
    ) {
        Box {
            Button(onClick = { expanded = true}) {
                Text("Sort")
            }
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                DropdownMenuItem(text = { Text("None") }, onClick = { onSortSelect(SortingOption.DEFAULT); expanded = false })
            }
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                DropdownMenuItem(text = { Text("Rating (High to Low") }, onClick = { onSortSelect(SortingOption.RATING_DESC); expanded = false })
            }
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                DropdownMenuItem(text = { Text("Rating (Low to High") }, onClick = { onSortSelect(SortingOption.RATING_ASC); expanded = false })
            }
        }
    }
    
    Button(onClick = onFilterClick) {
        Text(text = "Filter")
    }
}