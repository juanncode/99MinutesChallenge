package com.github.juanncode.data.repositories

import com.github.juanncode.common.safeapi.Resource
import com.github.juanncode.data.datasources.LocalDataSource
import com.github.juanncode.data.datasources.RemoteDataSource
import com.github.juanncode.enitities.Place

class PlaceRepository(private val remoteDataSource: RemoteDataSource, private val localDataSource: LocalDataSource, val key: String) {
    suspend fun getNearbyPlaces(location: String, radius: String): Resource<List<Place>> {
        return remoteDataSource.getNearbyPlaces(location, key, radius)
    }

    suspend fun getPlacesDb(): List<Place> {
        return localDataSource.getAllPlaces()
    }

    suspend fun insertPlaceDb(place: Place) {
        return localDataSource.insertPlace(place)
    }

    suspend fun updatePlaceDb(place: Place) {
        return localDataSource.updatePlace(place)
    }

    suspend fun deletePlaceDb(place: Place) {
        return localDataSource.deletePlace(place)
    }

    suspend fun findPlaceById(idPlace: String) : Place?{
        return localDataSource.findPlaceById(idPlace)
    }
}