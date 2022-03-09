package com.github.juanncode.challenge99minutes.ui.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.github.juanncode.challenge99minutes.R
import com.github.juanncode.challenge99minutes.databinding.ItemPlaceBinding
import com.github.juanncode.challenge99minutes.loadImage
import com.github.juanncode.enitities.Place
import kotlin.properties.Delegates

@SuppressLint("NotifyDataSetChanged")
class PlaceAdapter(private val listener: (place: Place) -> Unit): RecyclerView.Adapter<PlaceAdapter.ViewHolder>() {

    var places: List<Place> by Delegates.observable(listOf()) { property, oldValue, newValue ->
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<ItemPlaceBinding>(LayoutInflater.from(parent.context),
            R.layout.item_place, parent, false )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val place = places[position]
        holder.bind(place, listener)
    }

    override fun getItemCount(): Int {
        return places.size
    }

    class ViewHolder(private val binding: ItemPlaceBinding): RecyclerView.ViewHolder(binding.root) {
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