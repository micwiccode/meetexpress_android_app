package com.example.meetexpress


import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import java.time.LocalDate
import java.time.temporal.ChronoField
import kotlin.collections.ArrayList
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.checkbox.MaterialCheckBox
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.dialog_search.*
import kotlinx.android.synthetic.main.fragment_find_event.*
import java.text.SimpleDateFormat


/**
 * A simple [Fragment] subclass.
 */
class FindEventFragment : Fragment() {

    val db = FirebaseFirestore.getInstance()
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: FindEventRecyclerAdapter
    private lateinit var auth: FirebaseAuth
    lateinit var navBtn: ImageButton


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layout = inflater.inflate(R.layout.fragment_find_event, container, false)
        val searchBtn = layout.findViewById<ImageButton>(R.id.btn_search)

        searchBtn.setOnClickListener {
            openSearchDialog()
        }

        navBtn = layout.findViewById(R.id.nav_btn)

        if((this.activity as MenuActivity).dark) navBtn.setBackgroundResource(R.drawable.ic_menu_white_24dp)

        auth = FirebaseAuth.getInstance()

        navBtn.setOnClickListener{
            (activity as MenuActivity).openDrawer()
        }

        recyclerView = layout.findViewById(R.id.recycleView)
        recyclerView.layoutManager =
            LinearLayoutManager(activity?.applicationContext, RecyclerView.VERTICAL, false)
        val query = db.collection("events").orderBy("name", Query.Direction.ASCENDING)
        fetch(query)

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

    private fun fetch(query: Query) {
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


    private fun openSearchDialog() {

        val builder : AlertDialog.Builder = AlertDialog.Builder(requireContext())
        val view : View = LayoutInflater.from(activity).inflate(R.layout.dialog_search, null)
        val search = view.findViewById<Button>(R.id.search)
        val sportCheckBox = view.findViewById<MaterialCheckBox>(R.id.cb_sport)
        val cultureCheckBox = view.findViewById<MaterialCheckBox>(R.id.cb_culture)
        val educationCheckBox = view.findViewById<MaterialCheckBox>(R.id.cb_education)
        val esportCheckBox = view.findViewById<MaterialCheckBox>(R.id.cb_esport)
        val partyCheckBox = view.findViewById<MaterialCheckBox>(R.id.cb_party)
        val nearbySwitch = view.findViewById<SwitchMaterial>(R.id.switch_nearby)

        builder.setView(view)
        val dialog : AlertDialog = builder.create()
        dialog.window.attributes.windowAnimations = R.style.dialogAnimation
        dialog.show()

        search.setOnClickListener{
            val searchName = view.findViewById<EditText>(R.id.search_view).text.toString()
            val categories: ArrayList<String> = ArrayList()
            if (sportCheckBox.isChecked) categories.add("Sport")
            if (cultureCheckBox.isChecked) categories.add("Culture")
            if (partyCheckBox.isChecked) categories.add("Party")
            if (educationCheckBox.isChecked) categories.add("Education")
            if (esportCheckBox.isChecked) categories.add("E-sport")
//            sportCheckBox.setOnCheckedChangeListener { cb_sport, isChecked ->
//                // cos
//            }
//            cultureCheckBox.setOnCheckedChangeListener { cb_culture, isChecked ->
//                // cos
//            }
//            educationCheckBox.setOnCheckedChangeListener { cb_education, isChecked ->
//                // cos
//            }
//            esportCheckBox.setOnCheckedChangeListener { cb_esport, isChecked ->
//                // cos
//            }
//            partyCheckBox.setOnCheckedChangeListener { cb_party, isChecked ->
//                // cos
//            }
//            nearbySwitch.setOnCheckedChangeListener { switch_nearby, isChecked ->
//                // cos
//            }
            var query = db.collection("events").orderBy("name", Query.Direction.ASCENDING)
            if(searchName!=""){
                query = query.whereGreaterThanOrEqualTo("name", searchName)

            }
            if(categories.isNotEmpty()){
                query = query.whereIn("category", categories)
            }

            adapter.stopListening()
            fetch(query)
            adapter.startListening()
            dialog.dismiss()
        }
    }
}

