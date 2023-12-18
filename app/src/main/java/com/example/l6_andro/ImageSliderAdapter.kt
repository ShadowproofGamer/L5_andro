package com.example.l6_andro

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ImageSliderAdapter (private val photos: MutableList<ImageItem>) : RecyclerView.Adapter<ImageSliderAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_image_slider, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val photoUrl = photos[position].uripath
        Glide.with(holder.itemView)
            .load(photoUrl)
            .into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return photos.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }
}