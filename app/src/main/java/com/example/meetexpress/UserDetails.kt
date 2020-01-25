package com.example.meetexpress

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import android.graphics.BitmapFactory
import kotlinx.android.synthetic.main.activity_user_details.*
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import android.app.Activity
import android.content.SharedPreferences
import android.net.Uri
import androidx.core.graphics.drawable.RoundedBitmapDrawable
import androidx.core.graphics.drawable.toBitmap
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.io.FileNotFoundException
import java.lang.Exception


class UserDetails : AppCompatActivity() {

    private val RESULT_LOAD_IMG = 1
    private lateinit var auth: FirebaseAuth
    private val db = FirebaseFirestore.getInstance()
    private var storageRef = FirebaseStorage.getInstance().reference
    private var photoUri: Uri? = null
    private val privateMode = 0
    private val prefsFileName = "prefs"

    override fun onCreate(savedInstanceState: Bundle?) {

        val prefs: SharedPreferences = getSharedPreferences(prefsFileName, privateMode)
        val themeController = ThemeController()
        setTheme(themeController.changeTheme(prefs))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_details)
        auth = FirebaseAuth.getInstance()
        fillFromDb()
        val startImage = RoundedBitmapDrawableFactory.create(resources, BitmapFactory.decodeResource(resources, R.drawable.logo))
        setOvalShapedPhoto(startImage)

        change_photo_btn.setOnClickListener {
            changePhoto()
        }

        save.setOnClickListener {
            setNewPhoto()
            finish()
        }

        val editButtons= arrayOf(name_edit, surname_edit, age_edit, city_edit)
        editButtons.forEachIndexed { index, btn -> btn.setOnClickListener{ openDialogText(index) }}
    }

    private fun openDialogText(index :Int) {

        val builder : AlertDialog.Builder = AlertDialog.Builder(this)
        var view : View = LayoutInflater.from(this).inflate(R.layout.dialog_text, null)
        if(index == 2) {
            view = LayoutInflater.from(this).inflate(R.layout.dialog_number, null)
        }
        val confirm = view.findViewById<Button>(R.id.confirm)
        builder.setView(view)
        val dialog : AlertDialog = builder.create()
        dialog.window.attributes.windowAnimations = R.style.dialogAnimation
        dialog.show()

        confirm.setOnClickListener{
            val newValue = view.findViewById<TextInputEditText>(R.id.input_text_value).text.toString()
            if (newValue.isEmpty()) {
                view.findViewById<TextInputLayout>(R.id.input_layout_value).error = "Value is required"
            }
            else {
                changeValue(index, newValue)
                dialog.dismiss()
            }
        }
    }

    private fun changeValue(index: Int, newValue : String) {

        val profileRef = db.collection("profiles").document(auth.currentUser!!.uid)
        when(index){
            0 -> {
                name.text = newValue
                profileRef.update("name", newValue)
            }
            1 ->{
                surname.text = newValue
                profileRef.update("surname", newValue)
            }
            2 -> {
                age.text = newValue
                profileRef.update("age", newValue.toInt())
            }
            3 -> {
                city.text = newValue
                profileRef.update("city", newValue)
            }
        }
    }

    private fun changePhoto() {
        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"
        startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode === Activity.RESULT_OK) {
            try {
                val imageUri = data?.data
                photoUri = imageUri
                val imageStream = contentResolver.openInputStream(imageUri)
                val selectedImage = RoundedBitmapDrawableFactory.create(resources, imageStream)
                setOvalShapedPhoto(selectedImage)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
        } else {
        }
    }

    private fun setOvalShapedPhoto(image : RoundedBitmapDrawable) {
        image.isCircular = true
        profile_photo.setImageDrawable(null)
        profile_photo.setImageDrawable(image)
    }

    private fun fillFromDb(){

        val docRef = db.collection("profiles").document(auth.currentUser!!.uid)
        docRef.get().addOnSuccessListener { documentSnapshot ->
            val profile = documentSnapshot.toObject(Profile::class.java)
            name.text = profile!!.name
            city.text = profile.city
            age.text = profile.age.toString()
            surname.text = profile.surname

            storageRef.child("profiles/" + auth.currentUser!!.uid + ".jpg").downloadUrl
                .addOnSuccessListener {
                Picasso.get().load(it).resize(180, 180).centerCrop().into(profile_photo, object:
                    Callback {
                    override fun onSuccess() {
                        val bitmap = profile_photo.drawable.toBitmap()
                        val selectedImage = RoundedBitmapDrawableFactory.create(resources, bitmap)
                        selectedImage.isCircular = true
                        profile_photo.setImageDrawable(null)
                        profile_photo.setImageDrawable(selectedImage)            }

                    override fun onError(e: Exception?) {
                    }

                })
            }

        }

    }

    private fun setNewPhoto() {
        if(photoUri!=null){
            storageRef.child("profiles/" + auth.currentUser!!.uid + ".jpg").putFile(photoUri!!)
        }
    }
}
