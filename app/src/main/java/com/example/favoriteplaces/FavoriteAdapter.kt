package com.example.favoriteplaces


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView


class FavoriteAdapter(
   private val places: List<FavoritePlaces>,
   private val onPlaceClickListener: OnPlaceClickListener) :
    RecyclerView.Adapter<FavoriteAdapter.PlaceViewHolder>() {

    inner class PlaceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                onPlaceClickListener.onPlaceClick(position)
            }
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_place, parent, false)
        return PlaceViewHolder(view)

    }



        override fun getItemCount(): Int {
            return places.size + 1

        }

        override fun onBindViewHolder(holder: PlaceViewHolder, position: Int) {
                val place = places[position]
                holder.imageView.setImageResource(place.imageResourceId)
                holder.titleTextView.text = place.name


            }


        }




