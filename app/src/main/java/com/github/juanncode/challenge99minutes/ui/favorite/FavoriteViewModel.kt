package com.github.juanncode.challenge99minutes.ui.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.juanncode.enitities.Place
import com.github.juanncode.usecases.GetAllFavoritePlaces
import com.github.juanncode.usecases.OnClickedFavorite
import kotlinx.coroutines.launch

class FavoriteViewModel(private val getAllFavoritePlaces: GetAllFavoritePlaces, private val onClickedFavorite: OnClickedFavorite): ViewModel() {

    private val _placeLive = MutableLiveData<List<Place>>()
    val placeLive: LiveData<List<Place>> get() = _placeLive

    private val _removePlaceLive = MutableLiveData<Boolean>()
    val removePlaceLive: LiveData<Boolean> get() = _removePlaceLive

    fun getPlaces() {
        viewModelScope.launch {
            _placeLive.value = getAllFavoritePlaces.invoke()
        }
    }

    fun removeFavorite(place: Place) {
        viewModelScope.launch {
            onClickedFavorite.invoke(place)
            _removePlaceLive.value = true
        }
    }
}