package com.example.restaurantfinder.di

import com.example.restaurantfinder.data.network.JustEatApi
import com.example.restaurantfinder.data.repository.RestaurantRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    fun provideRestaurantRepository(api: JustEatApi): RestaurantRepository =
        RestaurantRepository(api)
}
