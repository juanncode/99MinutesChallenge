package com.github.juanncode.challenge99minutes

import com.github.juanncode.challenge99minutes.data.database.place.Place as DbPlace
import com.github.juanncode.challenge99minutes.data.service.models.responseApi.Geometry as ServiceGeometry
import com.github.juanncode.challenge99minutes.data.service.models.responseApi.Location as ServiceLocation
import com.github.juanncode.challenge99minutes.data.service.models.responseApi.Photo as ServicePhoto
import com.github.juanncode.challenge99minutes.data.service.models.responseApi.Place as ServicePlace
import com.github.juanncode.enitities.Geometry as DomainGeometry
import com.github.juanncode.enitities.Location as DomainLocation
import com.github.juanncode.enitities.Photo as DomainPhoto
import com.github.juanncode.enitities.Place as DomainPlace

fun DbPlace.toDomainPlace() = DomainPlace(
    business_status = business_status,
    geometry = com.github.juanncode.enitities.Geometry(location = com.github.juanncode.enitities.Location(lat = location.split(",")[0].toDouble(), lng = location.split(",")[1].toDouble())),
    icon = icon,
    icon_background_color = icon_background_color,
    icon_mask_base_uri = icon_mask_base_uri,
    name = name,
    permanently_closed = permanently_closed,
    photos = listOf(DomainPhoto(photo_reference = photo_reference)),
    place_id = place_id,
    rating = rating,
    reference = reference,
    scope = scope,
    types = null,
    user_ratings_total = user_ratings_total,
    vicinity = vicinity,
    favorite = favorite
)

fun DomainPlace.toDbPlace() = DbPlace(
    place_id = place_id,
    business_status = business_status,
    location = "${geometry?.location?.lat}, ${geometry?.location?.lng}",
    icon = icon,
    icon_background_color = icon_background_color,
    icon_mask_base_uri = icon_mask_base_uri,
    name = name,
    permanently_closed = permanently_closed,
    photo_reference = "${photos?.get(0)?.photo_reference}",
    rating = rating,
    reference = reference,
    scope = scope,
    user_ratings_total = user_ratings_total,
    vicinity = vicinity,
    favorite = favorite,
)

fun ServicePlace.toDomainPlace(): DomainPlace = DomainPlace(
    business_status = business_status,
    geometry = geometry.toDomainGeometry(),
    icon = icon,
    icon_background_color = icon_background_color,
    icon_mask_base_uri = icon_mask_base_uri,
    name = name,
    permanently_closed = permanently_closed,
    photos = photos?.let { p -> p.map { it.toDomainPhoto() } },
    place_id = place_id,
    rating = rating,
    reference = reference,
    scope = scope,
    types = types,
    user_ratings_total = user_ratings_total,
    vicinity = vicinity,
    favorite = false,
)

fun ServiceGeometry.toDomainGeometry() = DomainGeometry(
    location = location.toDomainLocation(),
)

fun ServiceLocation.toDomainLocation() = DomainLocation(
    lat, lng
)

fun ServicePhoto.toDomainPhoto(): DomainPhoto {
    return DomainPhoto(
        photo_reference, width
    )
}