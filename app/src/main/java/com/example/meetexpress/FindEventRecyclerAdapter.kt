package com.example.meetexpress

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.internal.ContextUtils.getActivity
import java.security.AccessController.getContext
import java.text.SimpleDateFormat
import java.time.LocalDate
import kotlin.collections.ArrayList

class FindEventRecyclerAdapter(options: FirestoreRecyclerOptions<Event>) :
    FirestoreRecyclerAdapter<Event, FindEventRecyclerAdapter.ViewHolder>(options) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: Event) {

        Log.d("TAG", "onBindViewHolder")
        val dateFormat = SimpleDateFormat("dd-MM-yyyy")
        holder.cardTitle.text = model.name
        holder.cardMembersActual.text = model.actualPeople.toString()
        holder.cardMembersMax.text = model.maxPeople.toString()
        holder.cardCategory.text = model.category
        holder.cardDate.text = dateFormat.format(model.date)
        holder.cardAddress.text = model.place
//        holder.cardImage.setImageResource(model.photo)

        val context = holder.cardView.context
        holder.cardView.setOnClickListener {
            val intent = Intent(context, EventDetails::class.java)
            intent.putExtra("model", model)
            context.startActivity(intent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.find_event_card_layout, parent, false)
        return ViewHolder(view)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val cardView: CardView = itemView.findViewById(R.id.cardView);
        val cardTitle: TextView = itemView.findViewById(R.id.cardTitle)
        val cardMembersActual: TextView = itemView.findViewById(R.id.cardMembersActual)
        val cardMembersMax: TextView = itemView.findViewById(R.id.cardMembersMax)
        val cardCategory: TextView = itemView.findViewById(R.id.cardCategory)
        val cardImage = itemView.findViewById<ImageView>(R.id.cardImage)
        val cardDate: TextView = itemView.findViewById(R.id.cardDate)
        val cardAddress: TextView = itemView.findViewById(R.id.cardAddress)

    }

}