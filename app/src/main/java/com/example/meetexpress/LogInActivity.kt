package com.example.meetexpress

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_log_in.*
import kotlinx.android.synthetic.main.activity_sign_up2.*
import kotlinx.android.synthetic.main.fragment_create_event.*


class LogInActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)
        mAuth = FirebaseAuth.getInstance()

        input_layout_login_email.setErrorTextAppearance(R.style.error_appearance)
        input_layout_login_password.setErrorTextAppearance(R.style.error_appearance)

        btn_login.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        val email = login_email.text.toString()
        val password = login_password.text.toString()

        if(validate(email, password)) {
            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                    this
                ) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("signIn", "signInWithEmail:success")
                        val user = mAuth.currentUser

                        val intent = Intent(this, MenuActivity::class.java)
                        // start your next activity
                        startActivity(intent)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("signIn", "signInWithEmail:failure", task.exception)
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

    private fun validate(email: String, password: String): Boolean {

        var isValid = true
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            input_layout_login_email.error = "Please enter valid email"
            isValid = false
        }
        else{
            input_layout_login_email.error = null
        }
        if (password.trim().length < 6) {
            input_layout_login_password.error = "Please enter password (at least 6 characters)"
            isValid = false
        }
        else{
            input_layout_login_password.error = null
        }

        return isValid
    }
}
