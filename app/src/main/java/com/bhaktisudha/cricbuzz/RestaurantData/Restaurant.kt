package com.example.kotlinnewsapirecycler.RestaurantData

import java.io.Serializable

data class Restaurant(
    var address: String ?= null,
    var cuisine_type: String ?= null,
    var id: Int ?= null,
    var latlng: Latlng ?= null,
    var name: String ?= null,
    var neighborhood: String ?= null,
    var operating_hours: OperatingHours ?= null,
    var photograph: String ?= null,
    var reviews: List<Review> ?= null
):Serializable