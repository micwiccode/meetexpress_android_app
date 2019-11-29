package com.example.meetexpress

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import java.text.SimpleDateFormat
import java.time.LocalDate
import kotlin.collections.ArrayList

class FindEventRecyclerAdapter(val options: FirestoreRecyclerOptions<Event>) :
    FirestoreRecyclerAdapter<Event, FindEventRecyclerAdapter.ViewHolder>(options) {
    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: Event) {
        val dateFormat = SimpleDateFormat("dd-MM-yyyy")
        Log.d("XDD", "XDD")
        holder.cardTitle.text = model.name
        holder.cardMembersActual.text = model.actualPeople.toString()
        holder.cardMembersMax.text = model.maxPeople.toString()
        holder.cardCategory.text = model.category
        holder.cardDate.text = dateFormat.format(model.date)
        holder.cardAddress.text = model.place
//        holder.cardImage.setImageResource(model.photo)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.find_event_card_layout, parent, false)
        return ViewHolder(view)
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