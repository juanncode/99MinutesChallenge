package com.github.juanncode.challenge99minutes.ui.main

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.juanncode.common.safeapi.Resource
import com.github.juanncode.enitities.Place
import com.github.juanncode.usecases.GetAllFavoritePlaces
import com.github.juanncode.usecases.GetNearbyPlaces
import kotlinx.coroutines.launch

class MainViewModel(
    private val getNearbyPlaces: GetNearbyPlaces,
    private val getAllFavoritePlaces: GetAllFavoritePlaces
) : ViewModel() {

    private val _placeLive = MutableLiveData<List<Place>>()
    val placeLive: LiveData<List<Place>> get() = _placeLive

    private val _favoritePlaceLive = MutableLiveData<List<Place>>()
    val favoritePlaceLive: LiveData<List<Place>> get() = _favoritePlaceLive

    private val _errorNetworkLive = MutableLiveData<Boolean>()
    val errorNetworkLive: LiveData<Boolean> get() = _errorNetworkLive

    private val _errorLive = MutableLiveData<String>()
    val errorLive: LiveData<String> get() = _errorLive

    fun getPlaces(location: Location) {
        viewModelScope.launch {
            val response = getNearbyPlaces.invoke(
                location = "${location.latitude}, ${location.longitude}",
                radius = "500"
            )
            when (response) {
                is Resource.Error -> _errorLive.value = response.data?.getMessage()
                Resource.NetworkError -> _errorNetworkLive.value = true
                is Resource.Success -> response.value?.let { _placeLive.value = it }
            }
        }
    }

    fun getFavoritePlaces() {
        viewModelScope.launch {
            _favoritePlaceLive.value = getAllFavoritePlaces.invoke()
        }
    }
}