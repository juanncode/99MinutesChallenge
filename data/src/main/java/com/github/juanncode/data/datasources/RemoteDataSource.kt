package com.github.juanncode.data.datasources

import com.github.juanncode.common.safeapi.Resource
import com.github.juanncode.enitities.Place

interface RemoteDataSource {
    suspend fun getNearbyPlaces(location: String, key: String, radius: String): Resource<List<Place>>
}