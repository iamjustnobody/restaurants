package com.example.restaurantfinder.data.model

data class Restaurant(
    val id: String,
    val name: String,
    val cuisines: List<String>,
    val rating: Rating,
    val address: Address
)

data class Rating(
    val starRating: Double,
    val count: Int
)

data class Address(
    val firstLine: String,
    val city: String,
    val postalCode: String
)
