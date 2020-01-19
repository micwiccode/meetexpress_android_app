package com.example.meetexpress

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception
import java.text.SimpleDateFormat

class MyEventsRecyclerAdapter(options: FirestoreRecyclerOptions<Event>) :
    FirestoreRecyclerAdapter<Event, MyEventsRecyclerAdapter.ViewHolder>(options) {
    private var storageRef = FirebaseStorage.getInstance().reference


    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
        model: Event
    ) {
        val context = holder.cardView.context
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val isMetered = cm.isActiveNetworkMetered


        val prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
        Log.d("XDDDD",prefs.getBoolean("transfer", false).toString() )
        val childRef =
            if(!prefs.getBoolean("transfer", false)){
                if(isMetered){
                    storageRef.child("events/" + snapshots.getSnapshot(position).id + "/"+ snapshots.getSnapshot(position).id+"_1.jpg")
                } else{
                    storageRef.child("events/" + snapshots.getSnapshot(position).id + "/"+ snapshots.getSnapshot(position).id+"_2.jpg")
                }
            }else{
                storageRef.child("events/" + snapshots.getSnapshot(position).id + "/"+ snapshots.getSnapshot(position).id+"_2.jpg")
            }
        val dateFormat = SimpleDateFormat("dd-MM-yyyy")
        holder.cardTitle.text = model.name
        holder.cardMembersActual.text = model.actualPeople.toString()
        holder.cardMembersMax.text = model.maxPeople.toString()
        holder.cardCategory.text = model.category
        holder.cardDate.text = dateFormat.format(model.date)
        holder.cardAddress.text = model.place
        holder.cardSlash.text = "/"

        holder.cardView.setOnClickListener {
            val intent = Intent(context, MyEventDetails::class.java)
            intent.putExtra("model", model)
            intent.putExtra("eventId", snapshots.getSnapshot(position).id)
            context.startActivity(intent)

        }
        childRef.downloadUrl.addOnSuccessListener {

            Picasso
                .get()
                .load(it)
                .into(holder.cardImage)


        }.addOnFailureListener{
            Log.d("ErrorImage", it.message)
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
        val cardSlash = itemView.findViewById<TextView>(R.id.Slash)
    }
}