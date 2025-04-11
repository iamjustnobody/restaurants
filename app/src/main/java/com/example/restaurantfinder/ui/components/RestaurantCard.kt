package com.example.restaurantfinder.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.restaurantfinder.R





@Composable
fun RestaurantCard(restaurant: Restaurant) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp).clickable { /* Handle click - could possible navigate to restaurant details screen */ },

//        shape = RoundedCornerShape(12.dp), //ok
        shape = androidx.compose.material3.MaterialTheme.shapes.medium.copy(CornerSize(12.dp)),
        elevation = CardDefaults.cardElevation(4.dp),
//        elevation = 4.dp,
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = rememberAsyncImagePainter(restaurant.logoUrl),
                contentDescription = restaurant.uniqueName,
                contentScale = ContentScale.Crop,
                modifier = Modifier.height(120.dp).fillMaxWidth().padding(bottom = 12.dp)
            )

            Text(restaurant.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)


            Text(
                restaurant.cuisines.joinToString(", ") { cuisine -> cuisine.name },
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(bottom = 8.dp)
            )

//          restaurant.rating?.starRating?.let { RatingBarXml(rating = it) }//ok
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(2.dp)) {
//              restaurant.rating?.starRating?.let { RatingBarXml(rating = it) }//ok
                restaurant.rating?.starRating?.let { RatingBar(rating = it) }
                Spacer(modifier = Modifier.width(2.dp))
                Text(text = "${restaurant.rating?.starRating}/5", style = MaterialTheme.typography.labelMedium, modifier = Modifier.padding(start = 4.dp))
            }

//            Text("${restaurant.address?.firstLine}, ${restaurant.address?.city}, ${restaurant.address?.postalCode}")
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.LocationOn,
                    contentDescription = "Location Icon",
                    modifier = Modifier.size(20.dp),
                    tint = Color.Gray
                )
                Text(
                    text = "${restaurant.address?.firstLine}, ${restaurant.address?.city}, ${restaurant.address?.postalCode}",
                    modifier = Modifier.padding(start = 8.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
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
fun RatingBarXml(rating: Double) {
    val fullStars = rating.toInt()
    val halfStar = if (rating % 1 >= 0.5) 1 else 0
    val emptyStars = 5 - fullStars - halfStar

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {

        repeat(fullStars) {
            Icon(painter = painterResource(id = R.drawable.star_filled), contentDescription = "Filled Star", tint = Color.Yellow)
        }


        if (halfStar > 0) {
            Icon(painter = painterResource(id = R.drawable.star_half), contentDescription = "Half Star", tint = Color.Yellow)
        }


        repeat(emptyStars) {
            Icon(painter = painterResource(id = R.drawable.star_empty), contentDescription = "Empty Star", tint = Color.Gray)
        }
    }
}



fun calculateStarRating(rating: Float): Pair<Int, Boolean> {
    val fullStars = rating.toInt()
    val hasHalfStar = rating - fullStars >= 0.5f
    return Pair(fullStars, hasHalfStar)
}

fun getEmptyStars(fullStars: Int, hasHalfStar: Boolean): Int {
    val totalStars = 5
    return if (hasHalfStar) {
        totalStars - fullStars - 1
    } else {
        totalStars - fullStars
    }
}
