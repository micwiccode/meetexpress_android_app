package com.example.meetexpress


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class ReviewEventsFragment : Fragment() {

    val db = FirebaseFirestore.getInstance()
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: ReviewEventsRecyclerAdapter
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layout = inflater.inflate(R.layout.fragment_review_events, container, false)

        val navBtn = layout.findViewById<ImageButton>(R.id.nav_btn)
        navBtn.setOnClickListener{
            (activity as MenuActivity).openDrawer()
        }
        auth = FirebaseAuth.getInstance()
        recyclerView = layout.findViewById(R.id.recycleView)
        recyclerView.layoutManager = LinearLayoutManager(activity?.applicationContext, RecyclerView.VERTICAL, false)

        val eventsList = ArrayList<Event>()
        val dateFormat = SimpleDateFormat("dd-MM-yyyy")
        val timeFormat =  SimpleDateFormat("hh:mm")
        var date = dateFormat.parse("10-11-2019").time
        var time = timeFormat.parse("11:00").time
        eventsList.add(Event("Play football", date, time,0,12,"Wrocław, Wittiga 15 Street", "Sport", R.drawable.sport_image))
        date = dateFormat.parse("09-11-2019").time
        time = timeFormat.parse("12:30").time
        eventsList.add(Event("FIFA 20 Tournament", date, time,4,48,"Wrocław, Sienkiewicza 2 Street", "E-sport", R.drawable.esport_image))
        fetch()

        return layout
    }

    private fun fetch() {
        val query = db.collection("events").whereArrayContains("profiles", auth.currentUser!!.uid)

        val options = FirestoreRecyclerOptions.Builder<Event>()
            .setQuery(
                query, Event::class.java)
            .build()


        adapter = ReviewEventsRecyclerAdapter(options)
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
