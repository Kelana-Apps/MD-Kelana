package com.example.kelanaa.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kelanaa.R
import com.example.kelanaa.data.response.Destinations
import com.example.kelanaa.data.response.DestinationsItem
import com.example.kelanaa.data.response.DestinationsResponse
import com.example.kelanaa.data.retrofit.ApiClient
import com.example.kelanaa.data.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DestinationsAdapter
    private val destinations = mutableListOf<Destinations>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = DestinationsAdapter(destinations) { destinations ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("destinations", destinations.id)
            startActivity(intent)
        }
        recyclerView.adapter = adapter

        fetchDestinations()
    }

    private fun fetchDestinations() {
        val apiService = ApiClient.getInstance().create(ApiService::class.java)
        apiService.getDestinations().enqueue(object : Callback<DestinationsResponse> {
            override fun onResponse(call: Call<DestinationsResponse>, response: Response<DestinationsResponse>) {
                Log.d("API Response", "Code: ${response.code()}, Body: ${response.body()}")
                if (response.isSuccessful && response.body() != null) {
                    destinations.addAll(mapDestinations(response.body()!!.destinations))
                    adapter.notifyDataSetChanged()
                } else {
                    Log.e("API Response", "Failed: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<DestinationsResponse>, t: Throwable) {
                Log.e("API Error", t.message ?: "Unknown Error")
                Toast.makeText(this@MainActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun mapDestinations(items: List<DestinationsItem>): List<Destinations> {
        return items.map { item: DestinationsItem ->
            Destinations(
                city = item.city,
                openingTime = item.openingTime,
                rating = item.rating,
                description = item.description,
                destinationId = item.destinationId,
                imgUrl = item.imgUrl,
                duration = item.duration,
                createdAt = item.createdAt,
                closingTime = item.closingTime,
                name = item.name,
                location = (item.location.latitude as? Double ?: 0.0), // Safely handle "Any" type
                id = item.id,
                category = item.category,
                priceRange = item.priceRange.min, // Use the "min" price from PriceRange
                updatedAt = item.updatedAt
            )
        }
    }
}