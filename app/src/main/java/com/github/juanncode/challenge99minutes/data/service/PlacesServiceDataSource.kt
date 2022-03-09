package com.github.juanncode.challenge99minutes.data.service

import com.github.juanncode.challenge99minutes.toDomainPlace
import com.github.juanncode.common.safeapi.Resource
import com.github.juanncode.common.safeapi.safeApiCall
import com.github.juanncode.data.datasources.RemoteDataSource
import com.github.juanncode.enitities.Place

class PlacesServiceDataSource: RemoteDataSource {
    override suspend fun getNearbyPlaces(location: String, key: String, radius: String): Resource<List<Place>> {
        return safeApiCall { RetrofitClient.service.getDataFromDirection(location, key, radius).results.map { it.toDomainPlace() }}
    }
}