package com.example.meetexpress


import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import java.time.LocalDate
import java.time.temporal.ChronoField
import kotlin.collections.ArrayList
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.fragment_create_event.*
import java.text.SimpleDateFormat


/**
 * A simple [Fragment] subclass.
 */
class FindEventFragment : Fragment() {

    val db = FirebaseFirestore.getInstance()
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: FindEventRecyclerAdapter
    private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layout = inflater.inflate(R.layout.fragment_find_event, container, false)
//        val searchButton = layout.findViewById<ImageButton>(R.id.btn_search)

        val navBtn = layout.findViewById<ImageButton>(R.id.nav_btn)
        auth = FirebaseAuth.getInstance()
        navBtn.setOnClickListener{
            (activity as MenuActivity).openDrawer()
        }

        recyclerView = layout.findViewById(R.id.recycleView)
        recyclerView.layoutManager =
            LinearLayoutManager(activity?.applicationContext, RecyclerView.VERTICAL, false)
        fetch()

        return layout
    }

    private fun getEvents(): ArrayList<Event> {
        val eventsList = ArrayList<Event>()
        db.collection("events")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val event = document.toObject(Event::class.java)
                    eventsList.add(event)
                    Log.d("Downloading", "${document.id} => ${document.data}")

                }
            }
            .addOnFailureListener { exception ->
                Log.w("Downloading", "Error getting documents.", exception)
            }

        return eventsList
    }

    private fun fetch() {
        val query = db.collection("events").orderBy("name", Query.Direction.ASCENDING)

        val options = FirestoreRecyclerOptions.Builder<Event>()
            .setQuery(
                query, Event::class.java)
            .build()


        adapter = FindEventRecyclerAdapter(options)
        adapter.userId = auth.currentUser!!.uid
        recyclerView.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

    private fun takePart(){

        db.collection("events")
    }
}

