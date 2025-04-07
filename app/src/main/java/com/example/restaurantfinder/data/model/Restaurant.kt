package com.example.restaurantfinder.data.model

data class Restaurant(
    val id: String,
    val name: String,
    val uniqueName: String,
    val cuisines: List<Cuisine> = emptyList(), // Default to empty list if not provided
    val rating: Rating? = null, // Nullable Rating
    val address: Address? = null // Nullable Address
)

data class Rating(
    val starRating: Double? = null,//Float? = null,//Double? = null, // Nullable  with default value null
    val count: Int = 0, // Default to 0 if not provided
    val userRating: Double? = null,//Float? = null //Double? = null // Nullable  with default value null
)

data class Address(
    val firstLine: String,
    val city: String,
    val postalCode: String
)

data class Cuisine(
    val name: String,
    val uniqueName: String
)
