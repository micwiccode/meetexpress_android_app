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
    private var dateTextView: TextView? =null
    private var timeTextView: TextView? =null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layout = inflater.inflate(R.layout.fragment_create_event, container, false)

        val navBtn = layout.findViewById<ImageButton>(R.id.nav_btn)
        navBtn.setOnClickListener{
            (activity as MenuActivity).openDrawer()
        }

        val calendar = Calendar.getInstance()
        year = calendar.get(Calendar.YEAR)
        month = calendar.get(Calendar.MONTH)
        day = calendar.get(Calendar.DAY_OF_MONTH)
        hour = calendar.get(Calendar.HOUR_OF_DAY)
        minute = calendar.get(Calendar.MINUTE)
        val datePickerBtn = layout.findViewById<ImageButton>(R.id.date_picker_btn)
        val timePickerBtn = layout.findViewById<ImageButton>(R.id.time_picker_btn)

        setDate(year, month, day)
        setTime(hour, minute)

        datePickerBtn.setOnClickListener{
            val dpd = DatePickerDialog(activity, DatePickerDialog.OnDateSetListener { _, mYear, mMonth, mDay   ->
                date_text_view.text = "" + String.format("%02d", mDay) + "-" + String.format("%02d", mMonth+1) + "-" + String.format("%02d", mYear)
            }, year, month, day )
            dpd.show()
        }

        timePickerBtn.setOnClickListener{
            val tpd = TimePickerDialog(activity, TimePickerDialog.OnTimeSetListener { _, mHour, mMinute   ->
                time_text_view.text = "" + String.format("%02d", mHour) + ":" + String.format("%02d", mMinute)
            }, hour, minute, true )
            tpd.show()
        }

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

    private fun setTime(hour: Int, minute: Int) {
        timeTextView?.text = "" + String.format("%02d", hour) + ":" + String.format("%02d", minute)
    }

    private fun setDate(year: Int, month: Int, day: Int) {
        dateTextView?.text = "" + String.format("%02d", day) + "-" + String.format("%02d", month+1) + "-" + String.format("%02d", year)
    }

    private fun addEvent() {
        val name = input_text_name.text.toString()
        val maxPeople = input_text_members.text.toString().toInt()
        val place = input_text_place.text.toString()
        val dateFormat =  SimpleDateFormat("dd-MM-yyyy")
        val date = dateFormat.parse(date_text_view.text.toString()).time
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


