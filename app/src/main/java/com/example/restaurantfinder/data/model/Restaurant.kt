package com.example.restaurantfinder.data.model

data class Restaurant(
    val id: String,
    val name: String,
    val uniqueName: String,
    val cuisines: List<Cuisine> = emptyList(), // Default to empty list if not provided
    val rating: Rating? = null, // Nullable Rating
    val address: Address? = null, // Nullable Address

    val isNew: Boolean?,
    val driveDistanceMeters: Int?,
    val openingTimeLocal: String?,
    val deliveryOpeningTimeLocal: String?,
    val deliveryEtaMinutes: DeliveryEta?,
    val isCollection: Boolean?,
    val isDelivery: Boolean?,
    val isOpenNowForCollection: Boolean?,
    val isOpenNowForDelivery: Boolean?,
    val isOpenNowForPreorder: Boolean?,
    val deliveryCost: Double?,
    val minimumDeliveryValue: Double?,
    val logoUrl: String?,
    val availability: Availability?,
)

data class Rating(
    val starRating: Double? = null,//Float? = null,//Double? = null,
    val count: Int = 0,
    val userRating: Double? = null,//Float? = null //Double? = null
)


data class Cuisine(
    val name: String,
    val uniqueName: String
)

data class DeliveryEta(
    val rangeLower: Int?,
    val rangeUpper: Int?
)

data class Availability(
    val delivery: Delivery?
)

data class Delivery(
    val isOpen: Boolean?,
    val canPreOrder: Boolean?,
    val nextAvailability: NextAvailability?,
    val etaMinutes: DeliveryEta?
)

data class NextAvailability(
    val from: String?
)

data class Address(
    val city: String,
    val firstLine: String,
    val postalCode: String,
    val location: Location
)

data class Location(
    val type: String,
    val coordinates: List<Double> // Longitude, Latitude
)