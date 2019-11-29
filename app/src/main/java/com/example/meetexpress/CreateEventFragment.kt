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
import java.time.Month
import java.time.MonthDay
import java.time.Year

/**
 * A simple [Fragment] subclass.
 */
class CreateEventFragment : Fragment() {

    private val db = FirebaseFirestore.getInstance()
    private var year = 2019
    private var month = 1
    private var day = 1
    private var dateTextView: TextView? =null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layout = inflater.inflate(R.layout.fragment_create_event, container, false)

        val calendar = Calendar.getInstance()
        year = calendar.get(Calendar.YEAR)
        month = calendar.get(Calendar.MONTH)
        day = calendar.get(Calendar.DAY_OF_MONTH)
        val datePickerBtn = layout.findViewById<ImageButton>(R.id.date_picker_btn)
        dateTextView = layout.findViewById(R.id.date_text_view)

        setDate(year, month, day)

        datePickerBtn.setOnClickListener{
            val dpd = DatePickerDialog(activity, DatePickerDialog.OnDateSetListener { _, mYear, mMonth, mDay   ->
                date_text_view.text = "" + mDay + "-" + mMonth + "-" + mYear
            }, year, month, day )
            dpd.show()
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

    private fun setDate(year: Int, month: Int, day: Int) {
        dateTextView?.text = "" + day + "-" + month + "-" + year
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


