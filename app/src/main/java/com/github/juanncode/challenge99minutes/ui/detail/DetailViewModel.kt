package com.github.juanncode.challenge99minutes.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.juanncode.enitities.Place
import com.github.juanncode.usecases.GetPlaceById
import com.github.juanncode.usecases.OnClickedFavorite
import kotlinx.coroutines.launch

class DetailViewModel(
    private val place: Place,
    private val onClickedFavorite: OnClickedFavorite,
    private val getPlaceById: GetPlaceById
) : ViewModel() {
    private val _favoriteClickedLive = MutableLiveData<Boolean>()
    val favoriteClickedLive: LiveData<Boolean> get() = _favoriteClickedLive

    private val _placeLive = MutableLiveData<Place?>()
    val placeLive: LiveData<Place?> get() = _placeLive

    init {
        findPlaceById()
    }

    private fun findPlaceById() {
        viewModelScope.launch {
            _placeLive.value = getPlaceById.invoke(place.place_id)
        }
    }

    fun onFavoriteClicked() {
        viewModelScope.launch {
            _favoriteClickedLive.value = onClickedFavorite.invoke(place)
        }
    }
}