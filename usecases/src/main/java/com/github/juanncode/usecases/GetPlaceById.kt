package com.github.juanncode.usecases

import com.github.juanncode.data.repositories.PlaceRepository
import com.github.juanncode.enitities.Place

class GetPlaceById(private val placeRepository: PlaceRepository) {

    suspend fun invoke(idPlace: String) : Place?{
        return placeRepository.findPlaceById(idPlace)
    }
}