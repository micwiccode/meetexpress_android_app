package com.example.meetexpress


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import android.os.Parcelable



/**
 * A simple [Fragment] subclass.
 */
class ReviewEventsFragment : Fragment() {

//    val db = FirebaseFirestore.getInstance()
//    lateinit var recyclerView: RecyclerView
//    lateinit var adapter: MyEventsRecyclerAdapter
//    private lateinit var auth: FirebaseAuth
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager
    private lateinit var myAdapter: ReviewEventsViewPagerAdapter
    private lateinit var myEvents: MyEventsFragment
    private lateinit var takePartEvents: TakePartEventsFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layout = inflater.inflate(R.layout.fragment_review_events, container, false)

        val navBtn = layout.findViewById<ImageButton>(R.id.nav_btn)
        if((this.activity as MenuActivity).dark) navBtn.setBackgroundResource(R.drawable.ic_menu_white_24dp)
        navBtn.setOnClickListener{
            (activity as MenuActivity).openDrawer()
        }

        myEvents = MyEventsFragment()
        takePartEvents = TakePartEventsFragment()

        tabLayout = layout.findViewById(R.id.tab_layout)
        viewPager =  layout.findViewById(R.id.view_pager)
        myAdapter = fragmentManager?.let { ReviewEventsViewPagerAdapter(it) }!!
        myAdapter.addFragment(myEvents, "Created by me")
        myAdapter.addFragment(takePartEvents, "Taking part in")
        viewPager.adapter = myAdapter
        tabLayout.setupWithViewPager(viewPager)
//        fragmentManager!!.beginTransaction().commit()



//        auth = FirebaseAuth.getInstance()
//        recyclerView = layout.findViewById(R.id.recycleView)
//        recyclerView.layoutManager = LinearLayoutManager(activity?.applicationContext, RecyclerView.VERTICAL, false)

//        val eventsList = ArrayList<Event>()
//        val dateFormat = SimpleDateFormat("dd-MM-yyyy")
//        val timeFormat =  SimpleDateFormat("hh:mm")
//        var date = dateFormat.parse("10-11-2019").time
//        var time = timeFormat.parse("11:00").time
//        eventsList.add(Event("Play football", date, time,0,12,"Wrocław, Wittiga 15 Street", "Sport", R.drawable.sport_image))
//        date = dateFormat.parse("09-11-2019").time
//        time = timeFormat.parse("12:30").time
//        eventsList.add(Event("FIFA 20 Tournament", date, time,4,48,"Wrocław, Sienkiewicza 2 Street", "E-sport", R.drawable.esport_image))
//        fetch()

        return layout
    }

    override fun onResume() {
        super.onResume()
        myAdapter = ReviewEventsViewPagerAdapter(childFragmentManager)
        myAdapter.addFragment(myEvents, "Created by me")
        myAdapter.addFragment(takePartEvents, "Taking part in")
        viewPager.adapter = myAdapter
        tabLayout.setupWithViewPager(viewPager)
    }



//    private fun fetch() {
//        val query = db.collection("events").whereArrayContains("profiles", auth.currentUser!!.uid)
//
//        val options = FirestoreRecyclerOptions.Builder<Event>()
//            .setQuery(
//                query, Event::class.java)
//            .build()
//
//
//        adapter = MyEventsRecyclerAdapter(options)
//        recyclerView.adapter = adapter
//    }
//
//    override fun onStart() {
//        super.onStart()
//        adapter.startListening()
//    }
//
//    override fun onStop() {
//        super.onStop()
//        adapter.stopListening()
//    }

}
