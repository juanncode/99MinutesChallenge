package com.github.juanncode.challenge99minutes.data.database

import com.github.juanncode.challenge99minutes.toDbPlace
import com.github.juanncode.challenge99minutes.toDomainPlace
import com.github.juanncode.data.datasources.LocalDataSource
import com.github.juanncode.enitities.Place
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RoomDataSource(db: PlacesDatabase): LocalDataSource {
    private val placeDao = db.placeDao()
    override suspend fun getAllPlaces(): List<Place> = withContext(Dispatchers.IO)  {
        placeDao.getAllPlaces().map { it.toDomainPlace() }
    }

    override suspend fun insertPlace(place: Place) = withContext(Dispatchers.IO)  {
        placeDao.insertPlace(place.toDbPlace())
    }

    override suspend fun updatePlace(place: Place) = withContext(Dispatchers.IO)  {
        placeDao.updatePlace(place.toDbPlace())
    }

    override suspend fun deletePlace(place: Place) = withContext(Dispatchers.IO)  {
        placeDao.deletePlace(place.toDbPlace())
    }

    override suspend fun findPlaceById(idPlace: String) : Place? = withContext(Dispatchers.IO) {
        placeDao.findPlaceById(idPlace)?.toDomainPlace()
    }
}