package com.example.meetexpress

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_log_in.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.activity_sign_up2.*
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T



class SignUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        nextButton.setOnClickListener {
            if (createUser()) {
                setContentView(R.layout.activity_sign_up2)

                btn_sign_up_confirm.setOnClickListener {
                    if (fillProfileData()) {
                        val intent = Intent(this, MenuActivity::class.java)
                        finish()
                        startActivity(intent)
                    }
                }

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
        private fun createUser(): Boolean {
            val email = emailText.text.toString()
            val password = passwordText.text.toString()
            val repeatPassword = repeatPasswordText.text.toString()
            Log.d("Email", email)
            Log.d("pas", password)
            Log.d("rpas", repeatPassword)
            if (validateFirstStep(email, password, repeatPassword)) {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(
                                baseContext,
                                "New profile created",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        } else {
                            Toast.makeText(
                                baseContext, "Authentication failed.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        // ...
                    }
                return true
            } else return false
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

        public fun validateNextStep(
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
            else if(age.trim().length > 3){
                ageLayout.error = "Please enter a valid age"
                isValid = false
                }
            else ageLayout.error = null

            if (city.isEmpty()) {
                cityLayout.error = "Please enter city"
                isValid = false
            }
            else{
                cityLayout.error = null
            }
            return isValid
        }

        public fun validateFirstStep(
            email: String,
            password: String,
            repeatPassword: String
        ): Boolean {

            var isValid = true

            if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailLayout.error = "Please enter valid email"
                isValid = false
            }
            else{
                emailLayout.error = null
            }
            if (password.trim().length < 6) {
                passwordLayout.error = "Please enter password (at least 6 characters)"
                isValid = false
            }
            else{
                passwordLayout.error = null
            }
//            if (repeatPassword.trim().length < 6) {
//                passwordLayout.error = "Please enter password (at least 6 characters)"
//                isValid = false
//            }
//            else{
//                repeatPasswordLayout.error = null
//            }
            if(!(password.trim().length < 6 && repeatPassword.trim().length < 6)){
                if (password != repeatPassword) {
                    passwordLayout.error = "Passwords are not the same"
                    isValid = false
                }
                else{
                    passwordLayout.error = null
                    repeatPasswordLayout.error = null
                }
            }
            return isValid
        }
    }
