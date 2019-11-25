package com.example.meetexpress

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_log_in.*


class LogInActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)
        mAuth = FirebaseAuth.getInstance()
        btn_login.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        val email = login_email.text.toString()
        val password = login_password.text.toString()
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(
                this
            ) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("XD", "signInWithEmail:success")
                    val user = mAuth.currentUser

                    val intent = Intent(this, MenuActivity::class.java)
                    // start your next activity
                    startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("xD", "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        this, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    //updateUI(null)
                }

                // ...
            }
    }
}
