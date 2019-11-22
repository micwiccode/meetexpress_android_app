package com.example.meetexpress


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
        val recycleView = layout.findViewById<RecyclerView>(R.id.recycleView)
        recycleView.layoutManager = LinearLayoutManager(activity?.applicationContext, RecyclerView.VERTICAL, false)

        val eventsList = ArrayList<Event>()
        val calendar = Calendar.getInstance()
        calendar.set(2019,11,10)
        eventsList.add(Event("Play football", calendar,0,12,"Wrocław, Wittiga 15 Street", "Sport", R.drawable.sport_image))
        calendar.set(2019,10,30)
        eventsList.add(Event("FIFA 20 Tournament", calendar,4,48,"Wrocław, Sienkiewicza 2 Street", "E-sport", R.drawable.esport_image))

        val adapter = ReviewEventRecycyleAdapter(eventsList)
        recycleView.adapter = adapter

        return layout
    }


}
