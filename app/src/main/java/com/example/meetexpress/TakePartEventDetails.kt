package com.example.meetexpress

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.measurement.module.Analytics
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
import kotlinx.android.synthetic.main.activity_take_part_event_details.*
import java.text.SimpleDateFormat

class TakePartEventDetails: AppCompatActivity() {

    private val privateMode = 0
    private val prefsFileName = "prefs"
    private var storageRef = FirebaseStorage.getInstance().reference
    private val db = FirebaseFirestore.getInstance()
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {

        val prefs: SharedPreferences = getSharedPreferences(prefsFileName, privateMode)
        val themeController = ThemeController()
        setTheme(themeController.changeTheme(prefs))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_take_part_event_details)
        auth = FirebaseAuth.getInstance()
        getIncomingIntent()
        not_take_part_btn.setOnClickListener {
            dontTakePart()
        }

    }

    private fun getIncomingIntent(){
        if(intent.hasExtra("model")){
            val event :Event = intent.getSerializableExtra("model") as Event
            val dateFormat = SimpleDateFormat("dd-MM-yyyy")
            val timeFormat = SimpleDateFormat("HH:mm")
            name.text = event.name
            membersActual.text = event.actualPeople.toString()
            membersMax.text = event.maxPeople.toString()
            category.text = event.category
            date.text = dateFormat.format(event.date)
            time.text = timeFormat.format(event.time)
            address.text = event.place
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

    private fun dontTakePart(){
        db.collection("events").document(intent.getStringExtra("eventId")).update("profiles", FieldValue.arrayRemove(auth.currentUser!!.uid))
        db.collection("profiles").document(auth.currentUser!!.uid).update("takingPartEvents", FieldValue.arrayRemove(intent.getStringExtra("eventId")));
        finish()
    }
}