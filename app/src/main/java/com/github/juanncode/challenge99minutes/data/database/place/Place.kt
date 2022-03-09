package com.github.juanncode.challenge99minutes.data.database.place

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Place(
    @PrimaryKey
    val place_id: String,
    val business_status: String?,
    val location: String,
    val icon: String?,
    val icon_background_color: String?,
    val icon_mask_base_uri: String?,
    val name: String?,
    val permanently_closed: Boolean?,
    val photo_reference: String,
    val rating: Double?,
    val reference: String?,
    val scope: String?,
    val user_ratings_total: Int?,
    val vicinity: String?,
    var favorite: Boolean
)