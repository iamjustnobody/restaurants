package com.example.restaurantfinder.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.Yellow

@Composable
fun RatingBar(rating: Double) {
    val fullStars = rating.toInt()  // Whole stars
    val remainingRating = rating - fullStars  // Remaining fractional part for partial star

    Row {
        // Draw full stars
        repeat(fullStars) {
            Canvas(modifier = Modifier.size(24.dp)) {
                drawStar(StarType.FULL)
            }
        }

        // Draw partial star (if applicable)
        if (remainingRating > 0) {
            Canvas(modifier = Modifier.size(24.dp)) {
                drawStar(StarType.PARTIAL, remainingRating)
            }
        }

        // Draw empty stars for the remaining part of the rating
        repeat(5 - fullStars - if (remainingRating > 0) 1 else 0) {
            Canvas(modifier = Modifier.size(24.dp)) {
                drawStar(StarType.EMPTY)
            }
        }

        // Display rating as text
//        Text(text = "${rating}/5", style = MaterialTheme.typography.titleSmall)
    }
}

enum class StarType {
    FULL, PARTIAL, EMPTY
}

// Draw the star with a custom split color based on the rating
fun DrawScope.drawStar(type: StarType, partialRating: Double = 0.0) {
    val path = androidx.compose.ui.graphics.Path().apply {
        moveTo(size.width / 2f, 0f)
        lineTo(size.width * 0.6f, size.height * 0.35f)
        lineTo(size.width * 0.98f, size.height * 0.35f)
        lineTo(size.width * 0.7f, size.height * 0.57f)
        lineTo(size.width * 0.8f, size.height * 0.95f)
        lineTo(size.width / 2f, size.height * 0.75f)
        lineTo(size.width * 0.2f, size.height * 0.95f)
        lineTo(size.width * 0.3f, size.height * 0.57f)
        lineTo(0f, size.height * 0.35f)
        lineTo(size.width * 0.4f, size.height * 0.35f)
        close()
    }

    when (type) {
        StarType.FULL -> drawPath(path, Yellow)  // Full star, all yellow
        StarType.PARTIAL -> {
            val fillWidth = size.width * partialRating // Fractional width of the star to fill
            val partialPath = androidx.compose.ui.graphics.Path().apply {
                moveTo(size.width / 2f, 0f)
                lineTo(size.width * 0.6f, size.height * 0.35f)
                lineTo(size.width * 0.98f, size.height * 0.35f)
                lineTo(size.width * 0.7f, size.height * 0.57f)
                lineTo(size.width * 0.8f, size.height * 0.95f)
                lineTo(size.width / 2f, size.height * 0.75f)
                lineTo(size.width * 0.2f, size.height * 0.95f)
                lineTo(size.width * 0.3f, size.height * 0.57f)
                lineTo(0f, size.height * 0.35f)
                lineTo(size.width * 0.4f, size.height * 0.35f)
                close()
            }

            // Drawing the yellow part of the star (based on partialRating)
            drawPath(partialPath, Yellow)

            // Draw the remaining gray part
            drawPath(path, Gray.copy(alpha = 0.5f)) // Use the original path for gray part
        }
        StarType.EMPTY -> drawPath(path, Gray.copy(alpha = 0.5f))  // Empty star, gray
    }
}




