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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layout = inflater.inflate(R.layout.fragment_find_event, container, false)
//        val searchButton = layout.findViewById<ImageButton>(R.id.btn_search)

        val navBtn = layout.findViewById<ImageButton>(R.id.nav_btn)
        navBtn.setOnClickListener{
            (activity as MenuActivity).openDrawer()
        }

        recyclerView = layout.findViewById(R.id.recycleView)
        recyclerView.layoutManager =
            LinearLayoutManager(activity?.applicationContext, RecyclerView.VERTICAL, false)
        fetch()

        val dateFormat =  SimpleDateFormat("dd-MM-yyyy")
        val timeFormat =  SimpleDateFormat("hh:mm")
        val eventsList = ArrayList<Event>()
        var date = dateFormat.parse("10-11-2019").time
        var time = timeFormat.parse("11:00").time
        eventsList.add(
            Event(
                "Play football",
                date,
                time,
                0,
                12,
                "Wrocław, Wittiga 15 Street",
                "Sport",
                R.drawable.sport_image
            )
        )
        date = dateFormat.parse("09-10-2019").time
        time = timeFormat.parse("12:30").time
        eventsList.add(
            Event(
                "FIFA 20 Tournament",
                date,
                time,
                4,
                48,
                "Wrocław, Sienkiewicza 2 Street",
                "E-sport",
                R.drawable.esport_image
            )
        )

        return layout
    }

    private fun getEvents(): ArrayList<Event> {
        val eventList = ArrayList<Event>()
        db.collection("events")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val event = document.toObject(Event::class.java)
                    eventList.add(event)
                    Log.d("Pobieramy", "${document.id} => ${document.data}")

                }
            }
            .addOnFailureListener { exception ->
                Log.w("Pobieramy", "Error getting documents.", exception)
            }

        return eventList
    }

    private fun fetch() {
        val query = db.collection("events").orderBy("name", Query.Direction.ASCENDING)

        val options = FirestoreRecyclerOptions.Builder<Event>()
            .setQuery(
                query, Event::class.java)
            .build()


        adapter = FindEventRecyclerAdapter(options)
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
}

