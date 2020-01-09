package com.example.meetexpress

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.measurement.module.Analytics
import kotlinx.android.synthetic.main.activity_event_details.*
import java.text.SimpleDateFormat
import android.content.Intent
import android.content.SharedPreferences
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.net.Uri


class FindEventDetails : AppCompatActivity() {

    private lateinit var addressInfo: String
    private val privateMode = 0
    private val prefsFileName = "prefs"

    override fun onCreate(savedInstanceState: Bundle?) {

        val prefs: SharedPreferences = getSharedPreferences(prefsFileName, privateMode)
        val themeController = ThemeController()
        setTheme(themeController.changeTheme(prefs))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_details)
        getIncomingIntent()

        map_btn.setOnClickListener {
            val mapUri = Uri.parse("geo:0,0?q=$addressInfo")
            val mapIntent = Intent(Intent.ACTION_VIEW, mapUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }
    }

    private fun getIncomingIntent() {
        if (intent.hasExtra("model")) {
            val event: Event = intent.getSerializableExtra("model") as Event
            val dateFormat = SimpleDateFormat("dd-MM-yyyy")
            val timeFormat = SimpleDateFormat("hh:mm")
            name.text = event.name
            membersActual.text = event.actualPeople.toString()
            membersMax.text = event.maxPeople.toString()
            category.text = event.category
            date.text = dateFormat.format(event.date)
            time.text = timeFormat.format(event.time)
            addressInfo = event.place
            address.text = addressInfo
        }
    }
}