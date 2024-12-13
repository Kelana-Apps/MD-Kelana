package com.example.kelanaa.data.response

import com.google.gson.annotations.SerializedName

data class DestinationsResponse(

	@field:SerializedName("destinations")
	val destinations: List<DestinationsItem>,
	val error: Boolean
)

data class DestinationsItem(

	@field:SerializedName("city")
	val city: String,

	@field:SerializedName("openingTime")
	val openingTime: Int,

	@field:SerializedName("rating")
	val rating: Double,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("destinationId")
	val destinationId: String,

	@field:SerializedName("imgUrl")
	val imgUrl: String,

	@field:SerializedName("duration")
	val duration: String,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("closingTime")
	val closingTime: Int,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("location")
	val location: Location,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("category")
	val category: List<String>,

	@field:SerializedName("priceRange")
	val priceRange: PriceRange,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)

data class PriceRange(

	@field:SerializedName("min")
	val min: Int,

	@field:SerializedName("max")
	val max: Int
)

data class Location(

	@field:SerializedName("_longitude")
	val longitude: Any,

	@field:SerializedName("_latitude")
	val latitude: Any
)
