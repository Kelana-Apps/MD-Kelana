package com.example.kelanaa.ui

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.kelanaa.R
import com.example.kelanaa.data.response.Destinations
import com.example.kelanaa.data.response.DestinationsItem
import com.example.kelanaa.data.retrofit.ApiClient
import com.example.kelanaa.data.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {
    private var destination: Destinations? = null // Initialize as nullable to avoid uninitialized access

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val tvNameDetail = findViewById<TextView>(R.id.tvNameDetail)
        val tvCityDetail = findViewById<TextView>(R.id.tvCityDetail)
        val tvDescriptionDetail = findViewById<TextView>(R.id.tvDescriptionDetail)
        val imgDestination = findViewById<ImageView>(R.id.imgDestination)

        val destinationID = intent.getStringExtra("destinations")
        if (destinationID.isNullOrEmpty()) {
            Toast.makeText(this, "Destination ID is missing!", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        fetchDestination(destinationID) { fetchedDestination ->
            // Update UI only after the destination is fetched
            destination = fetchedDestination
            destination?.let {
                tvNameDetail.text = it.name
                tvCityDetail.text = it.city
                tvDescriptionDetail.text = it.description
                Glide.with(this)
                    .load(it.imgUrl)
                    .into(imgDestination)
            }
        }
    }

    private fun fetchDestination(id: String, onFetched: (Destinations) -> Unit) {
        val apiService = ApiClient.getInstance().create(ApiService::class.java)
        apiService.getDestination(id).enqueue(object : Callback<DestinationsItem> {
            override fun onResponse(call: Call<DestinationsItem>, response: Response<DestinationsItem>) {
                if (response.isSuccessful && response.body() != null) {
                    val fetchedDestination = mapToDestination(response.body()!!, id)
                    onFetched(fetchedDestination)
                } else {
                    Log.e("API Response", "Failed: ${response.errorBody()?.string()}")
                    Toast.makeText(this@DetailActivity, "Failed to fetch destination details", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<DestinationsItem>, t: Throwable) {
                Log.e("API Error", t.message ?: "Unknown Error")
                Toast.makeText(this@DetailActivity, "Error fetching destination: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun mapToDestination(item: DestinationsItem, id: String): Destinations {
        return Destinations(
            city = item.city,
            openingTime = item.openingTime,
            rating = item.rating.toString().toDoubleOrNull() ?: 0.0, // Safely convert rating to Double
            description = item.description,
            destinationId = item.destinationId,
            imgUrl = item.imgUrl,
            duration = item.duration,
            createdAt = item.createdAt,
            closingTime = item.closingTime,
            name = item.name,
            location = 0.0, // Replace with actual conversion if needed (depends on latitude & longitude type)
            id = item.id ?: id,
            category = item.category,
            priceRange = item.priceRange.min, // Use min price from `PriceRange` as an example
            updatedAt = item.updatedAt
        )
    }
}