package com.bhaktisudha.cricbuzz

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinnewsapirecycler.RestaurantData.Restaurant
import java.util.ArrayList

class RestaurantAdapter(val context : Context, val listener:ItemClicked): RecyclerView.Adapter<RestaurantAdapter.NewViewHolder>() {
    var items = ArrayList<Restaurant>()
    inner class NewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val itemV = itemView.findViewById<TextView>(R.id.news_item_tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_layout,parent,false)
        val viewHolder = NewViewHolder(view)
        viewHolder.itemV.setOnClickListener{
            listener.itemClicked(items[viewHolder.adapterPosition])
        }
         return viewHolder

    }

    override fun onBindViewHolder(holder: NewViewHolder, position: Int) {
        val currentItem = items[position]
        holder.itemV.text = currentItem.name
    }

    override fun getItemCount(): Int {
       return items.size
    }

    fun updateList(newList:List<Restaurant>){
        items.clear()
        items.addAll(newList)
        items.size
        notifyDataSetChanged()
    }

}


interface ItemClicked{
    fun itemClicked(clickItem:Restaurant)
}