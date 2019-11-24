package com.example.meetexpress


import android.icu.util.Calendar
import android.icu.util.LocaleData
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_create_event.*
import kotlinx.android.synthetic.main.fragment_create_event.view.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoField

/**
 * A simple [Fragment] subclass.
 */
class CreateEventFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layout = inflater.inflate(R.layout.fragment_create_event, container, false)
        val spinner = layout.findViewById<Spinner>(R.id.categories_spinner)
        spinner.adapter = ArrayAdapter.createFromResource(activity!!.applicationContext, R.array.categories, android.R.layout.simple_spinner_dropdown_item)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
//                Toast.makeText(activity, "Selected $pos",Toast.LENGTH_LONG).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        layout.btn_create.setOnClickListener {
            addEvent()
        }
        return layout
    }

    fun addEvent() {
        val name = input_text_name.text.toString()
        val maxPeople = input_text_members.text.toString().toInt()
        val place = input_text_place.text.toString()
        val date = LocalDate.parse(input_text_date.text.toString(), DateTimeFormatter.ofPattern("dd.MM.yyyy")).getLong(ChronoField.EPOCH_DAY)
        val db = FirebaseFirestore.getInstance()
        val category = categories_spinner.selectedItem.toString()

        val event = Event(name, date, 0, maxPeople, place, category)

        db.collection("events")
            .add(event)
            .addOnSuccessListener { documentReference ->
                Log.d("Dodawanie", "Event added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w("Dodawanie", "Error adding document", e)
            }


    }



}


