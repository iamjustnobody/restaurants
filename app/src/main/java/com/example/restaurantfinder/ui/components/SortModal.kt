package com.example.restaurantfinder.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import com.example.restaurantfinder.ui.screens.home.SortingOption
import com.example.restaurantfinder.ui.screens.home.HomeViewModel


@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun SortModal(
    showModal: MutableState<Boolean>,
    selectedSortingOption: MutableState<SortingOption>,
    sortingOptions: Array<SortingOption>,
    viewModel: HomeViewModel
) {
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden) // Remember the Modal Bottom Sheet State

    if (showModal.value) {
        ModalBottomSheetLayout(
            sheetState = sheetState, // Using the correct SheetState here
            sheetContent = {
                Column(modifier = Modifier.fillMaxWidth()) {
                    sortingOptions.forEach { option ->
                        Text(
                            text = option.name,
                            modifier = Modifier
                                .clickable {
                                    selectedSortingOption.value = option
                                    showModal.value = false
                                    // Call the sorting function in the ViewModel
                                    viewModel.sortRestaurants(option)
                                }
                                .padding(16.dp)
                        )
                    }
                }
            },
            content = { /* Empty content */ }
        )
    }
}