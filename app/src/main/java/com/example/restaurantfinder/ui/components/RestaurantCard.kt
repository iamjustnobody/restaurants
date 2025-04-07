package com.example.restaurantfinder.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.restaurantfinder.data.model.Restaurant

import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.restaurantfinder.R





@Composable
fun RestaurantCard(restaurant: Restaurant) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp).clickable { /* Handle click */ },

        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp),
//        elevation = 4.dp,
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
//            if (restaurant.imageUrl != null) {
//                Image(painter = rememberImagePainter(restaurant.imageUrl), contentDescription = null)
//            }

            Text(restaurant.name, style = MaterialTheme.typography.titleMedium)
//            Text(text = restaurant.name, style = MaterialTheme.typography.h6)

//            Text(restaurant.cuisines.joinToString { it.name }, style = MaterialTheme.typography.bodySmall)
            Text(
                restaurant.cuisines.joinToString(", ") { cuisine -> cuisine.name },
                style = MaterialTheme.typography.bodySmall
            )
//            Text(text = "Cuisines: ${restaurant.cuisines.joinToString(", ")}", style = MaterialTheme.typography.body2)


//            RatingBar(rating = restaurant.rating.starRating)
//            restaurant.rating?.starRating?.let { RatingBar(rating = it) }//ok
            Row(verticalAlignment = Alignment.CenterVertically) {
//                Icon(Icons.Filled.Star, contentDescription = null)
                restaurant.rating?.starRating?.let { RatingBar(rating = it) }
                Text(text = "${restaurant.rating?.starRating}/5", style = MaterialTheme.typography.labelMedium)
            }

            Text("${restaurant.address?.firstLine}, ${restaurant.address?.city}, ${restaurant.address?.postalCode}")
//            Text(text = restaurant.address, style = MaterialTheme.typography.body2)
        }
    }
}

//@Composable
//fun RatingBar(rating: Float, modifier: Modifier = Modifier) {
////    val fullStars = rating.toInt()
////    val halfStar = rating % 1 >= 0.5
//    val (fullStars, halfStar) = calculateStarRating(rating)
//    val emptyStars = getEmptyStars(fullStars, halfStar)
//
//    Row(modifier = modifier) {
//        repeat(fullStars) {
//            Icon(Icons.Filled.Star, contentDescription = "Star", tint = Color.Yellow)
//        }
//        if (halfStar) {
////            Icon(Icons.Filled.StarHalf, contentDescription = "Half Star", tint = Color.Yellow)
//            Icon(Icons.Filled.StarBorder, contentDescription = "Half Star", tint = Color.Yellow)
//            Icon(painter = painterResource(id = R.drawable.star_half), contentDescription = "Half Star", tint = Color.Yellow)
//        }
//
//        repeat(emptyStars) {
//            Icon(Icons.Filled.StarBorder, contentDescription = "Empty Star", tint = Color.Gray)
//        }
//    }
//}

@Composable
fun RatingBar(rating: Double) {
    // Calculate the number of full, half, and empty stars
    val fullStars = rating.toInt()
    val halfStar = if (rating % 1 >= 0.5) 1 else 0
    val emptyStars = 5 - fullStars - halfStar

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Display the full stars
        repeat(fullStars) {
            Icon(painter = painterResource(id = R.drawable.star_filled), contentDescription = "Filled Star", tint = Color.Yellow)
        }

        // Display the half star if necessary
        if (halfStar > 0) {
            Icon(painter = painterResource(id = R.drawable.star_half), contentDescription = "Half Star", tint = Color.Yellow)
        }

        // Display the empty stars
        repeat(emptyStars) {
            Icon(painter = painterResource(id = R.drawable.star_empty), contentDescription = "Empty Star", tint = Color.Gray)
        }
    }
}



fun calculateStarRating(rating: Float): Pair<Int, Boolean> {
    val fullStars = rating.toInt() // Get the integer part (full stars)
    val hasHalfStar = rating - fullStars >= 0.5f // Check if there's a half star
    return Pair(fullStars, hasHalfStar)
}

fun getEmptyStars(fullStars: Int, hasHalfStar: Boolean): Int {
    val totalStars = 5
    return if (hasHalfStar) {
        totalStars - fullStars - 1 // Subtract one because half star occupies part of the last one
    } else {
        totalStars - fullStars // Empty stars fill the gap
    }
}
