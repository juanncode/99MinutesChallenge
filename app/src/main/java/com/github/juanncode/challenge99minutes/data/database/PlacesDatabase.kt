package com.github.juanncode.challenge99minutes.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.github.juanncode.challenge99minutes.data.database.place.Place

@Database(
    entities = [Place::class],
    version = 1
)
abstract class PlacesDatabase : RoomDatabase(){
    companion object {
        fun build(context: Context) =
            Room.databaseBuilder(context, PlacesDatabase::class.java, "place-db").build()
    }

    abstract fun placeDao(): PlaceDao
}