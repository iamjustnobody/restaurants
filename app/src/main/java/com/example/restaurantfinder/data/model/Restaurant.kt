package com.example.restaurantfinder.data.model

data class Restaurant(
    val id: String,
    val name: String,
    val uniqueName: String,
    val address: Address,
    val rating: Rating,
    val cuisines: List<Cuisine>,
)

data class Address(
    val city: String,
    val firstLine: String,
    val postalCode: String,
)

data class Rating(
    val count: Int,
    val starRating: Float,
)

data class Cuisine(
    val name: String
)
