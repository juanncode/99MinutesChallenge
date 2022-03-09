package com.github.juanncode.challenge99minutes.ui.detail

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.github.juanncode.challenge99minutes.R
import com.github.juanncode.challenge99minutes.databinding.ActivityDetailBinding
import com.github.juanncode.challenge99minutes.loadImage
import com.github.juanncode.challenge99minutes.ui.favorite.FavoriteActivity
import com.github.juanncode.enitities.Place
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailActivity : AppCompatActivity() {
    companion object {
        const val KEY_PLACE = "KEY_PLACE"
    }
    private lateinit var binding: ActivityDetailBinding

    val viewModel: DetailViewModel by viewModel{
        parametersOf(intent.extras?.getSerializable(KEY_PLACE) as Place)
    }
    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        binding.viewmodel = viewModel

        initView()
        initViewModel()
    }

    private fun initView() {
        val response = intent.extras?.getSerializable(KEY_PLACE) as Place
        response.let { place ->
            binding.imgPoster.loadImage("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=${place.photos?.get(0)?.photo_reference}&key=${getString(R.string.api_key)}")
            binding.txtTitle.text = place.name
            binding.txtDescription.text = place.business_status
        }

        binding.buttonBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun initViewModel() {
        viewModel.favoriteClickedLive.observe(this) {
            if (it) {
                binding.imageButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite_white))
            } else {
                binding.imageButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite_border))
            }
        }

        viewModel.placeLive.observe(this) {
            if (it!= null) {
                binding.imageButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite_white))
            } else {
                binding.imageButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_favorite_border))
            }
        }
    }
}