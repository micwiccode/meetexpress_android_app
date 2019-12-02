package com.example.meetexpress

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.activity_sign_up2.*

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val db = FirebaseFirestore.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        auth = FirebaseAuth.getInstance()

        nextButton.setOnClickListener {
            createUser()
            setContentView(R.layout.activity_sign_up2)

            btn_sign_up_confirm.setOnClickListener {
                fillProfileData()
                val intent = Intent(this, MenuActivity::class.java)
                startActivity(intent)
            }
        }
    }

    //    public override fun onStart() {
//        super.onStart()
//        // Check if user is signed in (non-null) and update UI accordingly.
//        val currentUser = auth.currentUser
//        updateUI(currentUser)
//    }
//
    private fun createUser() {
        val email = emailText.text.toString()
        val password = passwordText.text.toString()
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(baseContext, "New profile created", Toast.LENGTH_SHORT).show()



                } else {
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                // ...
            }
    }

    private fun fillProfileData(){
        val user = auth.currentUser
        val profile = Profile()
        profile.name = nameText.text.toString()
        profile.age = ageText.text.toString().toInt()
        profile.surname = surnameText.text.toString()
        profile.city = cityText.text.toString()
        db.collection("profiles").document(user!!.uid)
            .set(profile)
            .addOnSuccessListener {
                Log.d("Dodawanie", "Profile added")
            }
            .addOnFailureListener { e ->
                Log.w("Dodawanie", "Error adding document", e)
            }
    }
}
