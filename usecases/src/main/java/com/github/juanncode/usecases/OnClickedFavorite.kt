package com.github.juanncode.usecases

import com.github.juanncode.data.repositories.PlaceRepository
import com.github.juanncode.enitities.Place

class OnClickedFavorite(private val placeRepository: PlaceRepository) {
    suspend fun invoke(place: Place): Boolean {
        val placeResponse = placeRepository.findPlaceById(place.place_id)
        return if (placeResponse == null) {
            placeRepository.insertPlaceDb(place.copy(favorite = true))
            true
        } else {
            placeRepository.deletePlaceDb(placeResponse)
            false
        }
    }
}