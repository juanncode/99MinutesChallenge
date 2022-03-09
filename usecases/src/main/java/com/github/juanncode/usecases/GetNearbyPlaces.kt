package com.github.juanncode.usecases

import com.github.juanncode.common.safeapi.Resource
import com.github.juanncode.data.repositories.PlaceRepository
import com.github.juanncode.enitities.Place

class GetNearbyPlaces(private val placeRepository: PlaceRepository) {
    suspend fun invoke(location: String, radius: String) : Resource<List<Place>> {
        return placeRepository.getNearbyPlaces(location,radius)
    }
}