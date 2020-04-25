package com.wns.covidupdateapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.wns.covidupdateapp.SheetListsAdapter.MyViewHolder

class SheetListsAdapter(private val moviesList: MutableList<MutableList<String>>) :
    RecyclerView.Adapter<MyViewHolder>() {

    inner class MyViewHolder(view: View) : ViewHolder(view) {
        var tv_country: TextView
        var tv_date: TextView
        var tvTotal: TextView
        var tvDeaths: TextView
        var tvRecovered: TextView

        init {
            tv_country = view.findViewById<View>(R.id.tv_country) as TextView
            tv_date = view.findViewById<View>(R.id.tv_date) as TextView
            tvTotal = view.findViewById<View>(R.id.tvTotal) as TextView
            tvDeaths = view.findViewById<View>(R.id.tvDeaths) as TextView
            tvRecovered = view.findViewById<View>(R.id.tvRecovered) as TextView
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_details, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val movie = moviesList[position].toTypedArray()
        println("sout " + movie[0])
        holder.tv_country.setText(movie[0])
        // holder.tv_date.setText(movie[1])
        holder.tvTotal.setText(movie[1])
        holder.tvDeaths.setText(movie[2])
        holder.tvRecovered.setText(movie[3])
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

}