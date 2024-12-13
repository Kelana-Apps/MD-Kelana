package com.example.kelanaa.data.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Destinations(
    val city: String,
    val openingTime: Int,
    val rating: Double,
    val description: String,
    val destinationId: String,
    val imgUrl: String,
    val duration: String,
    val createdAt: String,
    val closingTime: Int,
    val name: String,
    val location: Double,
    val id: String,
    val category: List<String>,
    val priceRange: Int,
    val updatedAt: String
) : Parcelable
