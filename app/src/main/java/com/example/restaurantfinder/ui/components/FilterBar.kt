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
    val uiState by viewModel.uiState.collectAsState()

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

    if (uiState.isFilterDialogVisible) {
        AlertDialog(
            onDismissRequest = { viewModel.updateFilterDialogVisible(false) },
            title = { Text("Filters") },
            text = {
                Column {
                    FilterSwitch("New", uiState.filterOptions.isNew) {
                        viewModel.updateFilterOptions(uiState.filterOptions.copy(isNew = it))
                    }
                    FilterSwitch("Delivery", uiState.filterOptions.isDelivery) {
                        viewModel.updateFilterOptions(uiState.filterOptions.copy(isDelivery = it))
                    }
                    FilterSwitch("Collection", uiState.filterOptions.isCollection) {
                        viewModel.updateFilterOptions(uiState.filterOptions.copy(isCollection = it))
                    }
                    FilterSwitch("Open Now for Delivery", uiState.filterOptions.isOpenNowForDelivery) {
                        viewModel.updateFilterOptions(uiState.filterOptions.copy(isOpenNowForDelivery = it))
                    }
                    FilterSwitch("Open Now for Collection", uiState.filterOptions.isOpenNowForCollection) {
                        viewModel.updateFilterOptions(uiState.filterOptions.copy(isOpenNowForCollection = it))
                    }
                    FilterSwitch("Open Now for Preorder", uiState.filterOptions.isOpenNowForPreorder) {
                        viewModel.updateFilterOptions(uiState.filterOptions.copy(isOpenNowForPreorder = it))
                    }
                    FilterSwitch("Delivery Available", uiState.filterOptions.deliveryIsOpen) {
                        viewModel.updateFilterOptions(uiState.filterOptions.copy(deliveryIsOpen = it))
                    }
                    FilterSwitch("Can Preorder Delivery", uiState.filterOptions.deliveryCanPreOrder) {
                        viewModel.updateFilterOptions(uiState.filterOptions.copy(deliveryCanPreOrder = it))
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.updateFilterDialogVisible(false)
                        viewModel.applyFilters(uiState.restaurants)
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