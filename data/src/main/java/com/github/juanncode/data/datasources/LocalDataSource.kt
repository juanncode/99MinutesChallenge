package com.github.juanncode.data.datasources

import com.github.juanncode.enitities.Place

interface LocalDataSource {
    suspend fun getAllPlaces(): List<Place>
    suspend fun insertPlace(place: Place)
    suspend fun updatePlace(place: Place)
    suspend fun deletePlace(place: Place)
    suspend fun findPlaceById(idPlace: String): Place?
}