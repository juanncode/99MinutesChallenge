package com.github.juanncode.enitities

import java.io.Serializable

data class Place(
    val business_status: String?,
    val geometry: Geometry?,
    val icon: String?,
    val icon_background_color: String?,
    val icon_mask_base_uri: String?,
    val name: String?,
    val permanently_closed: Boolean?,
    val photos: List<Photo>?,
    val place_id: String,
    val rating: Double?,
    val reference: String?,
    val scope: String?,
    val types: List<String>?,
    val user_ratings_total: Int?,
    val vicinity: String?,
    var favorite: Boolean
): Serializable