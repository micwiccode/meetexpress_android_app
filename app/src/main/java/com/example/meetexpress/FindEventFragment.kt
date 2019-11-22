package com.example.meetexpress


import android.media.session.PlaybackState
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_find_event.*
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class FindEventFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layout = inflater.inflate(R.layout.fragment_find_event, container, false)
//        val searchButton = layout.findViewById<ImageButton>(R.id.btn_search)
        val recycleView = layout.findViewById<RecyclerView>(R.id.recycleView)
        recycleView.layoutManager = LinearLayoutManager(activity?.applicationContext, RecyclerView.VERTICAL, false)

        val eventsList = ArrayList<Event>()
        val calendar = Calendar.getInstance()
        calendar.set(2019,11,10)
        eventsList.add(Event("Play football", calendar,0,12,"Wrocław, Wittiga 15 Street", "Sport", R.drawable.sport_image))
        calendar.set(2019,10,30)
        eventsList.add(Event("FIFA 20 Tournament", calendar,4,48,"Wrocław, Sienkiewicza 2 Street", "E-sport", R.drawable.esport_image))

        val adapter = FindEventRecycyleAdapter(eventsList)
        recycleView.adapter = adapter

        return layout
    }
}
