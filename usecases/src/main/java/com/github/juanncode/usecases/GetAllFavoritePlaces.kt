package com.github.juanncode.usecases

import com.github.juanncode.data.repositories.PlaceRepository
import com.github.juanncode.enitities.Place

class GetAllFavoritePlaces(private val placeRepository: PlaceRepository) {

    suspend fun invoke(): List<Place> {
        return placeRepository.getPlacesDb()
    }
}