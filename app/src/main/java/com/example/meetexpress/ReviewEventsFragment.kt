package com.example.meetexpress


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.temporal.ChronoField
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class ReviewEventsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layout = inflater.inflate(R.layout.fragment_review_events, container, false)

        val navBtn = layout.findViewById<ImageButton>(R.id.nav_btn)
        navBtn.setOnClickListener{
            (activity as MenuActivity).openDrawer()
        }

        val recycleView = layout.findViewById<RecyclerView>(R.id.recycleView)
        recycleView.layoutManager = LinearLayoutManager(activity?.applicationContext, RecyclerView.VERTICAL, false)

        val eventsList = ArrayList<Event>()
        val dateFormat = SimpleDateFormat("dd-MM-yyyy")
        var date = dateFormat.parse("10-11-2019").time
        eventsList.add(Event("Play football", date,0,12,"Wrocław, Wittiga 15 Street", "Sport", R.drawable.sport_image))
        date = dateFormat.parse("09-11-2019").time
        eventsList.add(Event("FIFA 20 Tournament", date,4,48,"Wrocław, Sienkiewicza 2 Street", "E-sport", R.drawable.esport_image))

        val adapter = ReviewEventRecyclerAdapter(eventsList)
        recycleView.adapter = adapter

        return layout
    }


}
