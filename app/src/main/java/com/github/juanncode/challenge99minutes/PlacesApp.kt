package com.github.juanncode.challenge99minutes

import android.app.Application

class PlacesApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initDI()

    }
}