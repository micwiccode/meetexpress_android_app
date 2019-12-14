package com.example.meetexpress


import java.util.Calendar
import android.icu.util.LocaleData
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_create_event.*
import kotlinx.android.synthetic.main.fragment_create_event.view.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoField
import android.app.DatePickerDialog
import android.widget.*
import java.text.SimpleDateFormat
import android.app.TimePickerDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue

/**
 * A simple [Fragment] subclass.
 */
class CreateEventFragment : Fragment() {

    private val db = FirebaseFirestore.getInstance()
    private var year = 2019
    private var month = 1
    private var day = 1
    private var hour = 0
    private var minute = 0
    private lateinit var auth: FirebaseAuth



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layout = inflater.inflate(R.layout.fragment_create_event, container, false)
        auth = FirebaseAuth.getInstance()

        val navBtn = layout.findViewById<ImageButton>(R.id.nav_btn)
        navBtn.setOnClickListener {
            (activity as MenuActivity).openDrawer()
        }

        val spinner = layout.findViewById<Spinner>(R.id.categories_spinner)
        spinner.adapter = ArrayAdapter.createFromResource(
            activity!!.applicationContext,
            R.array.categories,
            android.R.layout.simple_spinner_dropdown_item
        )

        layout.btn_create.setOnClickListener {
            addEvent()
        }
        return layout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val calendar = Calendar.getInstance()
        year = calendar.get(Calendar.YEAR)
        month = calendar.get(Calendar.MONTH)
        day = calendar.get(Calendar.DAY_OF_MONTH)
        hour = calendar.get(Calendar.HOUR_OF_DAY)
        minute = calendar.get(Calendar.MINUTE)

        setDate(year, month, day)
        setTime(hour, minute)

        date_box.setOnClickListener {
            showDatePicker()
        }

        time_box.setOnClickListener {
            showTimePicker()
        }
    }

    private fun showTimePicker() {
        val tpd =
            TimePickerDialog(activity, TimePickerDialog.OnTimeSetListener { _, mHour, mMinute ->
                setTime(mHour, mMinute)
            }, hour, minute, true)
        tpd.show()
    }

    private fun showDatePicker() {
        val dpd = DatePickerDialog(
            activity,
            DatePickerDialog.OnDateSetListener { _, mYear, mMonth, mDay ->
                setDate(mYear, mMonth, mDay)
            },
            year, month, day
        )
        dpd.show()
    }

    private fun setTime(hour: Int, minute: Int) {
        time_text_view?.text = "" + String.format("%02d", hour) + ":" + String.format("%02d", minute)
    }

    private fun setDate(year: Int, month: Int, day: Int) {
        date_text_view?.text = "" + String.format("%02d", day) + "-" + String.format("%02d", month + 1) + "-" + String.format("%02d", year)
    }

    private fun addEvent() {

        val name = input_text_name.text.toString()
        val maxPeople = input_text_members.text.toString()
        val place = input_text_place.text.toString()
        val dateFormat = SimpleDateFormat("dd-MM-yyyy")
        val date = dateFormat.parse(date_text_view.text.toString()).time
        val timeFormat = SimpleDateFormat("hh:mm")
        val time = timeFormat.parse(time_text_view.text.toString()).time
        val category = categories_spinner.selectedItem.toString()

        if ( validate(name, maxPeople, place)) {

            val event = Event(name, date, time, 0, maxPeople.toInt(), place, category)

            val eventsCollection = db.collection("events")
            eventsCollection
                .add(event)
                .addOnSuccessListener { documentReference ->
                    Log.d("Dodawanie", "Event added with ID: ${documentReference.id}")
                    eventsCollection
                        .document(documentReference.id)
                        .update("profiles", FieldValue.arrayUnion(auth.currentUser!!.uid))
                    eventsCollection
                        .document(documentReference.id)
                        .update("creatorId", auth.currentUser!!.uid)

                    db.collection("profiles")
                        .document(auth.currentUser!!.uid)
                        .update("hostedEvents", FieldValue.arrayUnion(documentReference.id))


                }
                .addOnFailureListener { e ->
                    Log.w("Dodawanie", "Error adding document", e)
                }


        }
    }

    private fun validate(name: String, maxPeople: String, place: String) :Boolean {

        var isValid = true

        if (name.isEmpty()) {
            input_layout_name.error = "Name is required"
            isValid = false
        }
        if (maxPeople.isEmpty()) {
            input_layout_members.error = "Number of people is required"
            isValid = false
        }
        if (place.isEmpty()) {
            input_layout_place.error = "Place is required"
            isValid = false
        }
        return isValid
    }
}


