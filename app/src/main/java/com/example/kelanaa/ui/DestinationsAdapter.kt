package com.example.kelanaa.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.kelanaa.R
import com.example.kelanaa.data.response.Destinations

class DestinationsAdapter(
    private val destinations: MutableList<Destinations>,
    private val onItemClick: (Destinations) -> Unit
) :
    RecyclerView.Adapter<DestinationsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_destinations, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = destinations[position]
        holder.tvName.text = item.name
        holder.tvCity.text = item.city

        Glide.with(holder.itemView.context)
            .load(item.imgUrl)
            .into(holder.tvImage)

        holder.itemView.setOnClickListener {
            onItemClick(item)
        }
    }

    override fun getItemCount(): Int = destinations.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tvName)
        val tvCity: TextView = view.findViewById(R.id.tvCity)
        val tvImage: ImageView = view.findViewById(R.id.tvImage)
    }
}