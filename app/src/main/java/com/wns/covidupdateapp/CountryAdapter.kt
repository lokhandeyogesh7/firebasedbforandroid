package com.wns.covidupdateapp

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder

class CountryAdapter(
    private val moviesList: ArrayList<CountryModel>,
    private var itemClicked: clickedItem
) :
    RecyclerView.Adapter<CountryAdapter.MyViewHolder>() {

    inner class MyViewHolder(view: View) : ViewHolder(view) {
        var ivCountry: ImageView = view.findViewById<View>(R.id.ivCountry) as ImageView
        var tvCountry: TextView = view.findViewById<View>(R.id.tvCountry) as TextView
        var rlCountry: LinearLayout = view.findViewById<View>(R.id.rlCountry) as LinearLayout
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_countries, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val movie = moviesList[position]
        println("sout " + movie.country)
        holder.tvCountry.setText(movie.country)
        holder.ivCountry.setImageResource(movie.image)
        when (movie.type) {
            2 -> {
                holder.rlCountry.setBackgroundColor(Color.parseColor("#fff9eb"))
            }
            3 -> {
                holder.rlCountry.setBackgroundColor(Color.parseColor("#99DCDCDC"))
            }
            1 -> {
                holder.rlCountry.setBackgroundColor(Color.parseColor("#ffffff"))
            }
        }
        holder.itemView.setOnClickListener {
            itemClicked.itemClicked(movie.country)
        }
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    interface clickedItem {
        fun itemClicked(country: String)
    }

}