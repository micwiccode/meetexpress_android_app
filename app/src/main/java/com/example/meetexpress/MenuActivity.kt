package com.example.meetexpress

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_menu.*
import androidx.drawerlayout.widget.DrawerLayout
import android.content.Intent
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import androidx.core.graphics.drawable.toBitmap
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_user_details.*
import kotlinx.android.synthetic.main.navigation_header.*
import java.lang.Exception


class MenuActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener, NavigationView.OnNavigationItemSelectedListener {

    private val createFragment : CreateEventFragment = CreateEventFragment()
    private val findFragment : FindEventFragment = FindEventFragment()
    private val reviewFragment : ReviewEventsFragment = ReviewEventsFragment()
    private var drawerLayout : DrawerLayout? = null
    private lateinit var auth: FirebaseAuth
    private val db = FirebaseFirestore.getInstance()
    private var profile: Profile? = null
    private var storageRef = FirebaseStorage.getInstance().reference
    private var photoUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        auth = FirebaseAuth.getInstance()

        drawerLayout = findViewById(R.id.drawer_layout)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        bottomNavigationView.setOnNavigationItemSelectedListener(this)
        bottomNavigationView.selectedItemId = R.id.menu_create

        val navigationView = findViewById<NavigationView>(R.id.nav)
        navigationView.setNavigationItemSelectedListener(this)
        fillFromDb()

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.menu_create -> {
                supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.container, createFragment).commit()
                return true
            }
            R.id.menu_find -> {
                supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.container, findFragment).commit()
                return true
            }
            R.id.menu_review -> {
                supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).replace(R.id.container, reviewFragment).commit()
                return true
            }
            R.id.edit_account -> {
                val i = Intent(this@MenuActivity, UserDetails::class.java)
                startActivity(i)
                return true
            }
            R.id.log_out -> {
                finish()
                return true
            }
        }
        return false
    }

    fun openDrawer() {
        drawerLayout?.openDrawer(nav)
        user_name.text = profile!!.name
        user.text = profile!!.name + " " + profile!!.surname
        Picasso.get().load(photoUri).resize(85, 85).centerCrop().into(user_image, object: Callback {
            override fun onSuccess() {
                val bitmap = user_image.drawable.toBitmap()
                val selectedImage = RoundedBitmapDrawableFactory.create(resources, bitmap)
                selectedImage.isCircular = true
                user_image.setImageDrawable(null)
                user_image.setImageDrawable(selectedImage)            }

            override fun onError(e: Exception?) {
            }

        })


    }

    private fun fillFromDb() {

        val docRef = db.collection("profiles").document(auth.currentUser!!.uid)
        docRef.get().addOnSuccessListener { documentSnapshot ->
            profile = documentSnapshot.toObject(Profile::class.java)

        }
        storageRef.child("profiles/" + auth.currentUser!!.uid + ".jpg").downloadUrl.addOnSuccessListener {
            photoUri = it
        }
    }
}
