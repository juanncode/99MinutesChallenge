package com.github.juanncode.challenge99minutes.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.github.juanncode.challenge99minutes.R
import com.github.juanncode.challenge99minutes.bitmapDescriptorFromVector
import com.github.juanncode.challenge99minutes.databinding.ActivityMainBinding
import com.github.juanncode.challenge99minutes.permissions.PermissionRequester
import com.github.juanncode.challenge99minutes.showCustomDialog
import com.github.juanncode.challenge99minutes.ui.detail.DetailActivity
import com.github.juanncode.challenge99minutes.ui.favorite.FavoriteActivity
import com.github.juanncode.enitities.Place
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.Serializable

class MainActivity : AppCompatActivity(), OnMapReadyCallback, LocationListener {

    private var map: GoogleMap? = null
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModel()
    private lateinit var placeAdapter: PlaceAdapter
    private val listFavoritePlaces = mutableListOf<Place>()
    private val listAllPlacesOfService = mutableListOf<Place>()
    private val fineLocation =
        PermissionRequester(this, Manifest.permission.ACCESS_FINE_LOCATION, onDenied = {
            showDialog(message = "Es necesario otorgar el permiso de localización")
        }, onRationale = {
            showDialog(
                "Es importante otorgar el permiso de localización, presione aceptar para volver a intentarlo",
                true
            )
        })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initView()
        initViewModel()
    }

    private fun initView() {
        initMap()
        fineLocation.runWithPermission { getCurrentLocation() }
        binding.buttonFavorite.setOnClickListener {
            Intent(this@MainActivity, FavoriteActivity::class.java).also {
                startActivity(it)
            }
        }

        placeAdapter = PlaceAdapter { place ->
            Intent(this@MainActivity, DetailActivity::class.java)
                .putExtra(DetailActivity.KEY_PLACE, place as Serializable).also {
                    startActivity(it)
                }
        }
        binding.recyclerPlaces.adapter = placeAdapter
    }

    private fun initViewModel() {
        viewModel.placeLive.observe(this, placeObserver)
        viewModel.favoritePlaceLive.observe(this, favoritePlaceObserver)
        viewModel.errorLive.observe(this, errorObserver)
        viewModel.errorNetworkLive.observe(this, errorNetworkObserver)
    }

    private val placeObserver = Observer<List<Place>> { places ->
        hideProgress()
        fillPlaces(places)
        fillMarkers(places)
    }

    private val favoritePlaceObserver = Observer<List<Place>> { places ->
        fillFavoriteMarker(places)
    }

    private val errorObserver = Observer<String> { error ->
        hideProgress()
        showDialog(message = error)
    }

    private val errorNetworkObserver = Observer<Boolean> {
        hideProgress()
        binding.textErrorNetwork.visibility = View.VISIBLE

    }

    private fun showDialog(message: String, rational: Boolean = false) {
        showCustomDialog(
            title = getString(R.string.app_name),
            message = message
        ) { dialog, _ ->
            dialog.cancel()
            if (rational) {
                fineLocation.runWithPermission { getCurrentLocation() }
            }
        }
    }

    private fun fillFavoriteMarker(places: List<Place>) {
        listFavoritePlaces.clear()
        listFavoritePlaces.addAll(places)
        drawerAllMarkers()
    }

    private fun fillMarkers(places: List<Place>) {
        listAllPlacesOfService.clear()
        listAllPlacesOfService.addAll(places)

        drawerAllMarkers()
    }

    private fun drawerAllMarkers() {
        map?.clear()

        val listNoFavorite = getNoFavoritePlaces(listAllPlacesOfService)
        listNoFavorite.forEach { place ->
            val coordinates =
                LatLng(place.geometry?.location?.lat ?: 0.0, place.geometry?.location?.lng ?: 0.0)
            val marker = MarkerOptions().position(coordinates).title(place.name)
            map?.addMarker(marker)
        }
        listFavoritePlaces.forEach { place ->
            val coordinates =
                LatLng(place.geometry?.location?.lat ?: 0.0, place.geometry?.location?.lng ?: 0.0)

            val marker = MarkerOptions().position(coordinates).title(place.name)
                .icon(bitmapDescriptorFromVector(this, R.drawable.ic_favorite))
            map?.addMarker(marker)
        }
    }

    private fun getNoFavoritePlaces(places: List<Place>): List<Place> {
        val listNoFavorite = mutableListOf<Place>()
        places.forEach { place ->
            if (listFavoritePlaces.find { it.place_id == place.place_id } == null) {
                listNoFavorite.add(place)
            }
        }
        return listNoFavorite
    }

    private fun fillPlaces(places: List<Place>) {
        placeAdapter.places = getPlacesAccordingDistance(places.toMutableList())
    }

    //Método burbuja
    private fun getPlacesAccordingDistance(places: MutableList<Place>): List<Place> {
        val currentLocation = getLocationService()

        if (places.isNotEmpty() && currentLocation != null) {
            for (i in 0 until places.size - 1) {
                for (j in 0 until places.size - 1) {
                    val firstLocation = getSimpleLocation(places[j])
                    val firstDistance = currentLocation.distanceTo(firstLocation)
                    val secondDistance =
                        currentLocation.distanceTo(getSimpleLocation(places[j + 1]))
                    if (firstDistance > secondDistance) {
                        val aux = places[j]
                        places[j] = places[j + 1]
                        places[j + 1] = aux
                    }
                }
            }
            return places
        } else {
            return places
        }
    }

    private fun getSimpleLocation(place: Place): Location {
        val actualLocation = Location("first")
        actualLocation.latitude = place.geometry?.location?.lat ?: 0.0
        actualLocation.longitude = place.geometry?.location?.lng ?: 0.0
        return actualLocation
    }

    private fun getCurrentLocation() {
        moveCameraMap(getLocationService())
    }

    @SuppressLint("MissingPermission")
    private fun getLocationService(): Location? {
        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        val myLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER)
        map?.isMyLocationEnabled = true

        locationManager.requestLocationUpdates(
            LocationManager.NETWORK_PROVIDER,
            1000L,
            100f, this
        )
        return myLocation
    }

    private fun initMap() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
    }

    override fun onLocationChanged(location: Location) {
        getFavoritePlaces()
        getPlaces(location)
//        Toast.makeText(this, "-> ${location.latitude} ${location.longitude}", Toast.LENGTH_SHORT)
//            .show()
    }

    private fun moveCameraMap(location: Location?) {
        location?.let {
            val myLatLng = LatLng(
                it.latitude,
                it.longitude
            )
            val myPosition = CameraPosition.Builder()
                .target(myLatLng).zoom(17f).bearing(90f).tilt(30f).build()
            map?.animateCamera(CameraUpdateFactory.newCameraPosition(myPosition))
            getFavoritePlaces()
            getPlaces(location)
        }
    }

    private fun getPlaces(location: Location) {
        showProgress()
        viewModel.getPlaces(location)
    }

    private fun getFavoritePlaces() {
        viewModel.getFavoritePlaces()
    }

    private fun showProgress() {
        binding.progress.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        binding.progress.visibility = View.GONE
    }

    override fun onResume() {
        super.onResume()
        if (map != null) {
            getFavoritePlaces()
        }
    }
}