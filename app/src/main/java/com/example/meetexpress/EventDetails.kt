package com.example.meetexpress

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class EventDetails: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_details)
    }

    private fun getIncomingIntent(){
        if(intent.hasExtra("model")){
            val event = intent.getSerializableExtra("model")
        }
    }
}