package com.example.restaurantfinder.data.model

data class FilterOptions(
    val isNew: Boolean = false,
    val isCollection: Boolean = false,
    val isDelivery: Boolean = false,
    val isOpenNowForCollection: Boolean = false,
    val isOpenNowForDelivery: Boolean = false,
    val isOpenNowForPreorder: Boolean = false,
    val deliveryIsOpen: Boolean = false,
    val deliveryCanPreOrder: Boolean = false
)


