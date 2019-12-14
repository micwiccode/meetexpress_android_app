package com.example.meetexpress

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import java.text.SimpleDateFormat

class TakePartRecycleAdapter(options: FirestoreRecyclerOptions<Event>) :
    FirestoreRecyclerAdapter<Event, TakePartRecycleAdapter.ViewHolder>(options) {

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
        model: Event
    ) {
        val dateFormat = SimpleDateFormat("dd-MM-yyyy")
        holder.cardTitle.text = model.name
        holder.cardMembersActual.text = model.actualPeople.toString()
        holder.cardMembersMax.text = model.maxPeople.toString()
        holder.cardCategory.text = model.category
        holder.cardDate.text = dateFormat.format(model.date)
        holder.cardAddress.text = model.place

        val context = holder.cardView.context
        holder.cardView.setOnClickListener {
            val intent = Intent(context, TakePartEventDetails::class.java)
            intent.putExtra("model", model)
            context.startActivity(intent)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val view = LayoutInflater.from(parent?.context)
            .inflate(R.layout.review_event_card_layout, parent, false)
        return ViewHolder(view)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val cardView: CardView = itemView.findViewById(R.id.cardView)
        val cardTitle = itemView.findViewById<TextView>(R.id.cardTitle)
        val cardMembersActual = itemView.findViewById<TextView>(R.id.cardMembersActual)
        val cardMembersMax = itemView.findViewById<TextView>(R.id.cardMembersMax)
        val cardCategory = itemView.findViewById<TextView>(R.id.cardCategory)
        val cardImage = itemView.findViewById<ImageView>(R.id.cardImage)
        val cardDate = itemView.findViewById<TextView>(R.id.cardDate)
        val cardAddress = itemView.findViewById<TextView>(R.id.cardAddress)
    }
}