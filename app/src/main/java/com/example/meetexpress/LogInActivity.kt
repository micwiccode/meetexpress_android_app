package com.example.meetexpress

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_log_in.*

class LogInActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        btn_login.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            // start your next activity
            startActivity(intent)
        }
    }
}
