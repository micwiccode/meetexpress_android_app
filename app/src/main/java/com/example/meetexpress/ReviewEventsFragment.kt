package com.example.meetexpress


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
        val recycleView = layout.findViewById<RecyclerView>(R.id.recycleView)
        recycleView.layoutManager = LinearLayoutManager(activity?.applicationContext, RecyclerView.VERTICAL, false)

        val eventsList = ArrayList<Event>()
        var date = LocalDate.parse("2019-11-10").getLong(ChronoField.EPOCH_DAY)
        eventsList.add(Event("Play football", date,0,12,"Wrocław, Wittiga 15 Street", "Sport", R.drawable.sport_image))
        date = LocalDate.parse("2019-12-09").getLong(ChronoField.EPOCH_DAY)
        eventsList.add(Event("FIFA 20 Tournament", date,4,48,"Wrocław, Sienkiewicza 2 Street", "E-sport", R.drawable.esport_image))

        val adapter = ReviewEventRecyclerAdapter(eventsList)
        recycleView.adapter = adapter

        return layout
    }


}
