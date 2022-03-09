package com.github.juanncode.challenge99minutes

import android.app.Application
import com.github.juanncode.challenge99minutes.data.database.PlacesDatabase
import com.github.juanncode.challenge99minutes.data.database.RoomDataSource
import com.github.juanncode.challenge99minutes.data.service.PlacesServiceDataSource
import com.github.juanncode.challenge99minutes.ui.detail.DetailViewModel
import com.github.juanncode.challenge99minutes.ui.favorite.FavoriteViewModel
import com.github.juanncode.challenge99minutes.ui.main.MainActivity
import com.github.juanncode.challenge99minutes.ui.main.MainViewModel
import com.github.juanncode.data.datasources.LocalDataSource
import com.github.juanncode.data.datasources.RemoteDataSource
import com.github.juanncode.data.repositories.PlaceRepository
import com.github.juanncode.enitities.Place
import com.github.juanncode.usecases.GetAllFavoritePlaces
import com.github.juanncode.usecases.GetNearbyPlaces
import com.github.juanncode.usecases.GetPlaceById
import com.github.juanncode.usecases.OnClickedFavorite
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun Application.initDI() {
    startKoin {
        androidLogger(Level.ERROR)
        androidContext(this@initDI)
        modules(listOf(appModule, dataModule, scopesModule))
    }
}
private val appModule = module {
    single(named("apiKey")) { androidApplication().getString(R.string.api_key) }
    single { PlacesDatabase.build(androidContext()) }
    factory<LocalDataSource> { RoomDataSource(get()) }
    factory<RemoteDataSource> { PlacesServiceDataSource() }
}
private val dataModule = module {
    factory { PlaceRepository(get(),get(), get(named("apiKey")) ) }
}

private val scopesModule = module {
    viewModel { MainViewModel(get(), get()) }
    factory { GetNearbyPlaces(get()) }

    viewModel { (place: Place) -> DetailViewModel(place, get(), get()) }
    factory { OnClickedFavorite(get()) }
    factory { GetPlaceById(get()) }

    viewModel { FavoriteViewModel(get(), get()) }
    factory { GetAllFavoritePlaces(get()) }

}