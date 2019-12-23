package com.example.meetexpress

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MyEventsFragment : Fragment() {

    val db = FirebaseFirestore.getInstance()
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: MyEventsRecyclerAdapter
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val layout = inflater.inflate(R.layout.fragment_my_events, container, false)

        auth = FirebaseAuth.getInstance()
        recyclerView = layout.findViewById(R.id.recycleView)
        recyclerView.layoutManager = LinearLayoutManager(activity?.applicationContext, RecyclerView.VERTICAL, false)

        fetch()

        return layout
    }

    private fun fetch() {
        val query = db.collection("events").whereEqualTo("creatorId", auth.currentUser!!.uid)

        val options = FirestoreRecyclerOptions.Builder<Event>()
            .setQuery(
                query, Event::class.java)
            .build()


        adapter = MyEventsRecyclerAdapter(options)
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
