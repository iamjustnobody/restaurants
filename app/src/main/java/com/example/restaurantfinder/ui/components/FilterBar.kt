package com.example.restaurantfinder.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.restaurantfinder.ui.screens.home.HomeViewModel


@Composable
fun FilterBar(viewModel: HomeViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.End
    ) {
        IconButton(onClick = { viewModel.updateFilterDialogVisible(true) }) {
            Icon(Icons.Default.List, contentDescription = "Filter")
        }
    }

    if (viewModel.isFilterDialogVisible) {
        AlertDialog(
            onDismissRequest = { viewModel.updateFilterDialogVisible(false) },
            title = { Text("Filters") },
            text = {
                Column {
                    FilterSwitch("New", viewModel.filterOptions.isNew) {
                        viewModel.updateFilterOptions(viewModel.filterOptions.copy(isNew = it))
                    }
                    FilterSwitch("Delivery", viewModel.filterOptions.isDelivery) {
                        viewModel.updateFilterOptions(viewModel.filterOptions.copy(isDelivery = it))
                    }
                    FilterSwitch("Collection", viewModel.filterOptions.isCollection) {
                        viewModel.updateFilterOptions(viewModel.filterOptions.copy(isCollection = it))
                    }
                    FilterSwitch("Open Now for Delivery", viewModel.filterOptions.isOpenNowForDelivery) {
                        viewModel.updateFilterOptions(viewModel.filterOptions.copy(isOpenNowForDelivery = it))
                    }
                    FilterSwitch("Open Now for Collection", viewModel.filterOptions.isOpenNowForCollection) {
                        viewModel.updateFilterOptions(viewModel.filterOptions.copy(isOpenNowForCollection = it))
                    }
                    FilterSwitch("Open Now for Preorder", viewModel.filterOptions.isOpenNowForPreorder) {
                        viewModel.updateFilterOptions(viewModel.filterOptions.copy(isOpenNowForPreorder = it))
                    }
                    FilterSwitch("Delivery Available", viewModel.filterOptions.deliveryIsOpen) {
                        viewModel.updateFilterOptions(viewModel.filterOptions.copy(deliveryIsOpen = it))
                    }
                    FilterSwitch("Can Preorder Delivery", viewModel.filterOptions.deliveryCanPreOrder) {
                        viewModel.updateFilterOptions(viewModel.filterOptions.copy(deliveryCanPreOrder = it))
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.updateFilterDialogVisible(false)
                        val filtered = viewModel.applyFilters(viewModel.uiState.value.restaurants)
                        viewModel.updateFilteredRestaurants(filtered)
                    }
                ) {
                    Text("Apply")
                }
            }
        )
    }
}

@Composable
fun FilterSwitch(label: String, checked: Boolean, onToggle: (Boolean) -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label)
        Switch(checked = checked, onCheckedChange = onToggle)
    }
}