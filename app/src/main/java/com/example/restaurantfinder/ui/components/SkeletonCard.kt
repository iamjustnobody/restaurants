package com.example.restaurantfinder.ui.components

import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SkeletonCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(12.dp),
//        elevation = CardElevation(defaultElevation = 4.dp)//CardElevation(defaultElevation=4.dp,disabledElevation=0.dp,draggedElevation = 8.dp, focusedElevation=6.dp,hoveredElevation=6.dp,  pressedElevation= 8.dp),

    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Box(modifier = Modifier
                .height(150.dp)
                .background(Color.Gray.copy(alpha = 0.3f))
            )
            Spacer(modifier = Modifier.height(8.dp))
            Box(modifier = Modifier
                .height(20.dp)
                .background(Color.Gray.copy(alpha = 0.3f))
            )
            Spacer(modifier = Modifier.height(4.dp))
            Box(modifier = Modifier
                .height(16.dp)
                .background(Color.Gray.copy(alpha = 0.3f))
            )
        }
    }
}
