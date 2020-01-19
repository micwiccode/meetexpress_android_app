package com.example.meetexpress

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_sign_up2.*

class SignUpGoogleActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()

        setContentView(R.layout.activity_sign_up2)

        btn_sign_up_confirm.setOnClickListener {
            if (fillProfileData()) {
                val intent = Intent(this, MenuActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun fillProfileData(): Boolean {
        val name = nameText.text.toString()
        val surname = surnameText.text.toString()
        val age = ageText.text.toString()
        val city = cityText.text.toString()
        if (validateNextStep(name, surname, age, city)) {
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
            return true
        } else return false
    }

    private fun validateNextStep(
        name: String,
        surname: String,
        age: String,
        city: String
    ): Boolean {

        var isValid = true
        if (name.trim().length < 2) {
            nameLayout.error = "Please enter a valid name"
            isValid = false
        }
        else{
            nameLayout.error = null
        }
        if (surname.trim().length < 2) {
            surnameLayout.error = "Please enter a valid surname"
            isValid = false
        }
        else{
            surnameLayout.error = null
        }
        if (age.isEmpty()) {
            ageLayout.error = "Please enter age"
            isValid = false
        }
        else{
            ageLayout.error = null
        }
        if (city.isEmpty()) {
            cityLayout.error = "Please enter city"
            isValid = false
        }
        else{
            cityLayout.error = null
        }
        return isValid
    }
}
