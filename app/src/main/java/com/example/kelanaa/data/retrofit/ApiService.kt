package com.example.kelanaa.data.retrofit

import com.example.kelanaa.data.response.DestinationsItem
import com.example.kelanaa.data.response.DestinationsResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("destinations")
    fun getDestinations(): Call<DestinationsResponse>

    @GET("destinations/{id}")
    fun getDestination(@Path("id") id: String): Call<DestinationsItem>
}