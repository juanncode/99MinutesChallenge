package com.github.juanncode.challenge99minutes.ui.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.github.juanncode.challenge99minutes.R
import com.github.juanncode.challenge99minutes.databinding.ActivityFavoriteBinding
import com.github.juanncode.challenge99minutes.ui.main.MainViewModel
import com.github.juanncode.enitities.Place
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoriteActivity : AppCompatActivity() {
    private val viewModel: FavoriteViewModel by viewModel()
    private lateinit var binding : ActivityFavoriteBinding
    private lateinit var adapter: FavoriteAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_favorite)
        adapter = FavoriteAdapter {
            viewModel.removeFavorite(it)
        }
        binding.recyclerFavorites.adapter = adapter

        viewModel.getPlaces()

        viewModel.placeLive.observe(this) {
            adapter.places = listOf()
            adapter.places = it
        }
        viewModel.removePlaceLive.observe(this) {
            viewModel.getPlaces()
        }

        binding.buttonBack.setOnClickListener {
            onBackPressed()
        }
    }
}