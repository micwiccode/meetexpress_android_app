package com.example.meetexpress

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat

class ReviewEventRecyclerAdapter(val eventsList: ArrayList<Event>) : RecyclerView.Adapter<ReviewEventRecyclerAdapter.ViewHolder>(){

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dateFormat = SimpleDateFormat("dd-MM-yyyy")
        val event : Event = eventsList[position]
        holder.cardTitle.text = event.name
        holder.cardMembersActual.text = event.actualPeople.toString()
        holder.cardMembersMax.text = event.maxPeople.toString()
        holder.cardCategory.text = event.category
        holder.cardDate.text = dateFormat.format(event.date)
        holder.cardAddress.text = event.place
        holder.cardImage.setImageResource(event.photo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.review_event_card_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return eventsList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val cardTitle = itemView.findViewById<TextView>(R.id.cardTitle)
        val cardMembersActual = itemView.findViewById<TextView>(R.id.cardMembersActual)
        val cardMembersMax = itemView.findViewById<TextView>(R.id.cardMembersMax)
        val cardCategory = itemView.findViewById<TextView>(R.id.cardCategory)
        val cardImage = itemView.findViewById<ImageView>(R.id.cardImage)
        val cardDate = itemView.findViewById<TextView>(R.id.cardDate)
        val cardAddress = itemView.findViewById<TextView>(R.id.cardAddress)


    }
}