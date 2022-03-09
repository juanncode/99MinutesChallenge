package com.github.juanncode.challenge99minutes.data.service.models.responseApi

data class ResponseApi(
    val html_attributions: List<Any>,
    val next_page_token: String,
    val results: List<Place>,
    val status: String
)