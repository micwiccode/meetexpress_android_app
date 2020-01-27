package com.example.meetexpress

import android.content.Context
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
import android.net.ConnectivityManager
import android.net.Uri
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso


class FindEventDetails : AppCompatActivity() {

    private lateinit var addressInfo: String
    private val privateMode = 0
    private val prefsFileName = "prefs"
    private var storageRef = FirebaseStorage.getInstance().reference
    private lateinit var auth: FirebaseAuth
    var eventId: String = ""
    val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {

        auth = FirebaseAuth.getInstance()
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
        take_part_btn.setOnClickListener {
            takePart()
        }
    }

    private fun getIncomingIntent() {
        if (intent.hasExtra("model")) {
            val event: Event = intent.getSerializableExtra("model") as Event
            val dateFormat = SimpleDateFormat("dd-MM-yyyy")
            val timeFormat = SimpleDateFormat("HH:mm")
            name.text = event.name
            membersActual.text = event.actualPeople.toString()
            membersMax.text = event.maxPeople.toString()
            category.text = event.category
            date.text = dateFormat.format(event.date)
            time.text = timeFormat.format(event.time)
            addressInfo = event.place
            address.text = addressInfo
            val prefs = getSharedPreferences("prefs", Context.MODE_PRIVATE)
            val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val isMetered = cm.isActiveNetworkMetered
            eventId = intent.getStringExtra("eventId")
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
                        "events/" + intent.getStringExtra("eventId") + "/"+ intent.getStringExtra("eventId")+"_1.jpg"
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
    private fun takePart() {
        db.collection("events").document(eventId).update("profiles", FieldValue.arrayUnion(auth.currentUser!!.uid))
        db.collection("profiles").document(auth.currentUser!!.uid)
            .update("takingPartEvents", FieldValue.arrayUnion(eventId))
        db.collection("events").document(eventId).update("actualPeople", FieldValue.increment(1))
        finish()
    }
}