package com.example.meetexpress

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlinx.android.synthetic.main.activity_start.*

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        btn_log_in.setOnClickListener {
            val intent = Intent(this, LogInActivity::class.java)
            // start your next activity
            startActivity(intent)
        }

        btn_signup.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            // start your next activity
            startActivity(intent)
        }
    }
}
