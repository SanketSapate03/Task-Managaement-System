package com.example.todolistapp

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.Locale

/*
class Adapter(var data: List<CardInfo>) : RecyclerView.Adapter<Adapter.viewHolder>() {

    //making view holder
    class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title = itemView.findViewById<TextView>(R.id.title)
        var priority = itemView.findViewById<TextView>(R.id.priority)
        var layout = itemView.findViewById<TextView>(R.id.myLayout)
    }


    //converting int type file to view file type
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.view, parent, false)
        return viewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {

        when (data[position].priority.toLowerCase(Locale.ROOT)) {
            "high" -> holder.layout.setBackgroundColor(Color.parseColor("F05454"))
            "medium" -> holder.layout.setBackgroundColor(Color.parseColor("EDC988"))
            else -> holder.layout.setBackgroundColor(Color.parseColor("00917C"))
        }

        holder.title.text = data[position].title
        holder.priority.text = data[position].priority

        //when click on the holder
        holder.itemView.setOnClickListener(){
            val intent=Intent(holder.itemView.context, UpdateCard::class.java)
            intent.putExtra("id",position)
            holder.itemView.context.startActivity(intent)
        }
    }
}*/

class Adapter(var data: List<CardInfo>) : RecyclerView.Adapter<Adapter.ViewHolder>() {

    // Making ViewHolder
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title = itemView.findViewById<TextView>(R.id.title)
        var priority = itemView.findViewById<TextView>(R.id.priority)
        var layout = itemView.findViewById<View>(R.id.myLayout) // Change TextView to View
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.view, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (data[position].priority.toLowerCase(Locale.ROOT)) {
            "high" -> holder.layout.setBackgroundColor(Color.parseColor("#F05454"))
            "medium" -> holder.layout.setBackgroundColor(Color.parseColor("#EDC988"))
            else -> holder.layout.setBackgroundColor(Color.parseColor("#00917C"))
        }

        holder.title.text = data[position].title
        holder.priority.text = data[position].priority

        // Handle item click
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, UpdateCard::class.java)
            intent.putExtra("id", position)
            holder.itemView.context.startActivity(intent)
        }
    }
}
