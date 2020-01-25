package com.example.meetexpress

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_event_details.*
import kotlinx.android.synthetic.main.activity_event_details.address
import kotlinx.android.synthetic.main.activity_event_details.category
import kotlinx.android.synthetic.main.activity_event_details.date
import kotlinx.android.synthetic.main.activity_event_details.image
import kotlinx.android.synthetic.main.activity_event_details.membersActual
import kotlinx.android.synthetic.main.activity_event_details.membersMax
import kotlinx.android.synthetic.main.activity_event_details.name
import kotlinx.android.synthetic.main.activity_event_details.time
import kotlinx.android.synthetic.main.activity_my_event_details.*
import kotlinx.android.synthetic.main.fragment_create_event.*
import kotlinx.android.synthetic.main.fragment_create_event.date_box
import kotlinx.android.synthetic.main.fragment_create_event.time_box
import java.text.SimpleDateFormat

class MyEventDetails: AppCompatActivity() {

    private lateinit var event:Event
    private lateinit var builder : AlertDialog.Builder
    private lateinit var view : View
    private lateinit var eventDate : TextView
    private lateinit var eventTime : TextView
    private val dateFormat = SimpleDateFormat("dd-MM-yyyy")
    private val timeFormat = SimpleDateFormat("HH:mm")
    private val privateMode = 0
    private val prefsFileName = "prefs"
    private var storageRef = FirebaseStorage.getInstance().reference
    private var eventId: String = ""
    private val db = FirebaseFirestore.getInstance()
    private lateinit var auth: FirebaseAuth





    override fun onCreate(savedInstanceState: Bundle?) {

        val prefs: SharedPreferences = getSharedPreferences(prefsFileName, privateMode)
        val themeController = ThemeController()
        setTheme(themeController.changeTheme(prefs))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_event_details)

        getIncomingIntent()
        auth = FirebaseAuth.getInstance()

        val modifyBtn = findViewById<MaterialButton>(R.id.modify_btn)

        modifyBtn.setOnClickListener {
            openModifyDialog()
        }
        delete_btn.setOnClickListener {
            deleteEvent()
        }
    }

    private fun deleteEvent(){
        db.collection("events").document(eventId).delete()
            .addOnSuccessListener {  }
            .addOnFailureListener {  }
        db.collection("profiles").document()
            .update("takingPartEvents", FieldValue.arrayRemove(eventId))
        db.collection("profiles").document(auth.currentUser!!.uid)
            .update("hostedEvents", FieldValue.arrayRemove(eventId))
        Toast.makeText(this, "Event has been successfully deleted" , Toast.LENGTH_SHORT).show()
        finish()
    }

    private fun getIncomingIntent(){
        if(intent.hasExtra("model")){
            event = intent.getSerializableExtra("model") as Event
            name.text = event.name
            membersActual.text = event.actualPeople.toString()
            membersMax.text = event.maxPeople.toString()
            category.text = event.category
            date.text = dateFormat.format(event.date)
            time.text = timeFormat.format(event.time)
            address.text = event.place
            eventId = intent.getStringExtra("eventId")!!
            val prefs = getSharedPreferences("prefs", Context.MODE_PRIVATE)
            val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val isMetered = cm.isActiveNetworkMetered

            val childRef =
                if (!prefs.getBoolean("transfer", false)) {
                    if (isMetered) {
                        storageRef.child(
                            "events/" + intent.getStringExtra("eventId") + "/"+ intent.getStringExtra("eventId")+"_1.jpg"
                        )
                    } else {
                        storageRef.child(
                            "events/" + intent.getStringExtra("eventId") + "/"+ intent.getStringExtra("eventId")+"_2.jpg"
                        )
                    }
                } else {
                    storageRef.child(
                        "events/" + intent.getStringExtra("eventId") + "/"+ intent.getStringExtra("eventId")+"_2.jpg"
                    )
                }
            childRef.downloadUrl.addOnSuccessListener {
                Log.d("XDDD", "1")

                Picasso
                    .get()
                    .load(it)
                    .into(image)
            }
            }
    }

    private fun openModifyDialog(){

        builder = AlertDialog.Builder(this)
        view = LayoutInflater.from(this).inflate(R.layout.dialog_modify_event, null)

        val eventName = view.findViewById<TextInputEditText>(R.id.event_name)
        val eventPlace = view.findViewById<TextInputEditText>(R.id.event_place)
        val eventMembers = view.findViewById<TextInputEditText>(R.id.event_members)
        eventDate = view.findViewById(R.id.event_date_text_view)
        eventTime = view.findViewById(R.id.event_time_text_view)
        val sportCheckBox = view.findViewById<RadioButton>(R.id.cb_sport)
        val cultureCheckBox = view.findViewById<RadioButton>(R.id.cb_culture)
        val educationCheckBox = view.findViewById<RadioButton>(R.id.cb_education)
        val esportCheckBox = view.findViewById<RadioButton>(R.id.cb_esport)
        val partyCheckBox = view.findViewById<RadioButton>(R.id.cb_party)
        val dateBox = view.findViewById<LinearLayout>(R.id.date_box)
        val timeBox = view.findViewById<LinearLayout>(R.id.time_box)
        val save = view.findViewById<MaterialButton>(R.id.save_btn)
        val back = view.findViewById<MaterialButton>(R.id.back_btn)

        eventName.setText(event.name)
        eventPlace.setText(event.place)
        eventMembers.setText(event.maxPeople.toString())
        eventDate.text = dateFormat.format(event.date)
        eventTime.text = timeFormat.format(event.time)
        when(event.category){
            "Sport"->
                sportCheckBox.isChecked = true
            "Culture"->
                cultureCheckBox.isChecked = true
            "Education"->
                educationCheckBox.isChecked = true
            "E-sport"->
                esportCheckBox.isChecked = true
            "Party"->
                partyCheckBox.isChecked = true
        }

        builder.setView(view)
        val dialog : AlertDialog = builder.create()
        dialog.window.attributes.windowAnimations = R.style.dialogAnimation
        dialog.show()

        save.setOnClickListener{
            var category = if(sportCheckBox.isChecked) "Sport"
            else {
                if(cultureCheckBox.isChecked) "Culture"
                else{
                    if(educationCheckBox.isChecked) "Education"
                    else{
                        if(partyCheckBox.isChecked) "Party"
                        else
                            "E-sport"
                    }
                }
            }
            db.collection("events").document(eventId).update(mapOf(
                "category" to category,
                "maxPeople" to eventMembers.text.toString().toInt(),
                "name" to eventName.text.toString(),
                "place" to eventPlace.text.toString(),
                "date" to dateFormat.parse(eventDate.text.toString()).time,
                "time" to timeFormat.parse(eventTime.text.toString()).time
            ))
            dialog.dismiss()
            Toast.makeText(this, "Even has beed successfully modified", Toast.LENGTH_SHORT).show()
            finish()
        }

        back.setOnClickListener{
            dialog.dismiss()
        }

        dateBox.setOnClickListener {
            showDatePicker(
                SimpleDateFormat("yyyy").format(event.date).toInt(),
                SimpleDateFormat("MM").format(event.date).toInt()-1,
                SimpleDateFormat("dd").format(event.date).toInt())
        }

        timeBox.setOnClickListener {
            showTimePicker(
                SimpleDateFormat("HH").format(event.time).toInt(),
                SimpleDateFormat("mm").format(event.time).toInt())
        }
    }

    private fun showTimePicker(hour:Int, minute: Int) {
        val tpd =
            TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { _, mHour, mMinute ->
                setTime(mHour, mMinute)
            }, hour, minute, true)
        tpd.show()
    }

    private fun showDatePicker(year: Int, month: Int, day:Int) {
        val dpd = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, mYear, mMonth, mDay ->
                setDate(mYear, mMonth, mDay)
            },
            year, month, day
        )
        dpd.show()
    }

    private fun setTime(hour: Int, minute: Int) {
        eventTime.text = "" + String.format("%02d", hour) + ":" + String.format("%02d", minute)
    }

    private fun setDate(year: Int, month: Int, day: Int) {
        eventDate.text = "" + String.format("%02d", day) + "-" + String.format("%02d", month + 1) + "-" + String.format("%02d", year)
    }
}