package com.example.meetexpress

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat

class FindEventRecyclerAdapter(options: FirestoreRecyclerOptions<Event>) :
    FirestoreRecyclerAdapter<Event, FindEventRecyclerAdapter.ViewHolder>(options) {

    val db = FirebaseFirestore.getInstance()

    var userId: String = ""
    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: Event) {


        if (model.profiles.contains(userId) || model.creatorId == userId) {
            holder.itemView.visibility = View.GONE
            holder.itemView.layoutParams = RecyclerView.LayoutParams(0, 0)
        } else {
            holder.itemView.visibility = View.VISIBLE
            holder.itemView.layoutParams =
                RecyclerView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
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
                val intent = Intent(context, FindEventDetails::class.java)
                intent.putExtra("model", model)
                context.startActivity(intent)
            }
        }
    }

    private fun takePart(eventId: String){
        db.collection("events").document(eventId).update("profiles", FieldValue.arrayUnion(userId))
        db.collection("profiles").document(userId).update("hostedEvents", FieldValue.arrayUnion(eventId))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.find_event_card_layout, parent, false)
        return ViewHolder(view)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val cardView: CardView = itemView.findViewById(R.id.cardView)
        val cardTitle: TextView = itemView.findViewById(R.id.cardTitle)
        val cardMembersActual: TextView = itemView.findViewById(R.id.cardMembersActual)
        val cardMembersMax: TextView = itemView.findViewById(R.id.cardMembersMax)
        val cardCategory: TextView = itemView.findViewById(R.id.cardCategory)
        val cardImage = itemView.findViewById<ImageView>(R.id.cardImage)
        val cardDate: TextView = itemView.findViewById(R.id.cardDate)
        val cardAddress: TextView = itemView.findViewById(R.id.cardAddress)
        val takePartBTN: Button = itemView.findViewById(R.id.takePartButton)

    }

}