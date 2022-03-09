package com.github.juanncode.challenge99minutes.ui.favorite

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.github.juanncode.challenge99minutes.R
import com.github.juanncode.challenge99minutes.databinding.ItemFavoriteBinding
import com.github.juanncode.challenge99minutes.loadImage
import com.github.juanncode.enitities.Place
import kotlin.properties.Delegates

@SuppressLint("NotifyDataSetChanged")
class FavoriteAdapter(private val listener: (place: Place) -> Unit): RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    var places: List<Place> by Delegates.observable(listOf()) { _, _, _ ->
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<ItemFavoriteBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_favorite, parent, false )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val place = places[position]
        holder.bind(place, listener)
    }

    override fun getItemCount(): Int {
        return places.size
    }

    class ViewHolder(private val binding: ItemFavoriteBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(place: Place, listener: (place: Place) -> Unit) {
            binding.textTitle.text = place.name
            binding.textDescription.text = place.reference
            binding.image.loadImage("https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference=${place.photos?.get(0)?.photo_reference}&key=AIzaSyCGVMxXh4H3nPIlBL-J7Z0ot9a_b_KkWmE")
            binding.buttonNext.setOnClickListener {
                listener(place)
            }
        }
    }

}