package com.github.juanncode.challenge99minutes.data.database

import androidx.room.*
import com.github.juanncode.challenge99minutes.data.database.place.Place

@Dao
interface PlaceDao {
    @Query("SELECT * FROM Place")
    fun getAllPlaces(): List<Place>

    @Query("SELECT * FROM Place where place_id = :idPlace")
    fun findPlaceById(idPlace: String): Place?

    @Insert
    fun insertPlace(place: Place)

    @Update
    fun updatePlace(place: Place)

    @Delete
    fun deletePlace(place: Place)

}