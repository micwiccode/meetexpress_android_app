package com.example.meetexpress

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.measurement.module.Analytics
import kotlinx.android.synthetic.main.activity_event_details.*
import java.text.SimpleDateFormat

class TakePartEventDetails: AppCompatActivity() {

    private val privateMode = 0
    private val prefsFileName = "prefs"

    override fun onCreate(savedInstanceState: Bundle?) {

        val prefs: SharedPreferences = getSharedPreferences(prefsFileName, privateMode)
        val themeController = ThemeController()
        setTheme(themeController.changeTheme(prefs))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_take_part_event_details)
        getIncomingIntent()
    }

    private fun getIncomingIntent(){
        if(intent.hasExtra("model")){
            val event :Event = intent.getSerializableExtra("model") as Event
            val dateFormat = SimpleDateFormat("dd-MM-yyyy")
            val timeFormat = SimpleDateFormat("hh:mm")
            name.text = event.name
            membersActual.text = event.actualPeople.toString()
            membersMax.text = event.maxPeople.toString()
            category.text = event.category
            date.text = dateFormat.format(event.date)
            time.text = timeFormat.format(event.time)
            address.text = event.place
        }
    }
}