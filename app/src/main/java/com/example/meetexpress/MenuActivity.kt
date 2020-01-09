package com.example.meetexpress

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_menu.*
import androidx.drawerlayout.widget.DrawerLayout
import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.navigation_header.*
import android.widget.ImageButton


class MenuActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener,
    NavigationView.OnNavigationItemSelectedListener {

    private val createFragment: CreateEventFragment = CreateEventFragment()
    private val findFragment: FindEventFragment = FindEventFragment()
    private val reviewFragment: ReviewEventsFragment = ReviewEventsFragment()
    private var drawerLayout: DrawerLayout? = null
    private lateinit var auth: FirebaseAuth
    private val db = FirebaseFirestore.getInstance()
    var dark: Boolean = false
    private var playSound = false
    private lateinit var player: MediaPlayer
    private var vibrate = false

    override fun onCreate(savedInstanceState: Bundle?) {
        readValuesFromStorage()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        auth = FirebaseAuth.getInstance()

        drawerLayout = findViewById(R.id.drawer_layout)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        bottomNavigationView.setOnNavigationItemSelectedListener(this)
        bottomNavigationView.selectedItemId = R.id.menu_create

        val navigationView = findViewById<NavigationView>(R.id.nav)
        navigationView.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.menu_create -> {
                supportFragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                    .replace(R.id.container, createFragment).commit()
                if (playSound) player.start()
                if (vibrate) vibratePhone()

                return true
            }
            R.id.menu_find -> {
                supportFragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                    .replace(R.id.container, findFragment).commit()
                if (playSound) player.start()
                if (vibrate) vibratePhone()

                return true
            }
            R.id.menu_review -> {
                supportFragmentManager.beginTransaction()
                    .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
                    .replace(R.id.container, reviewFragment).commit()
                if (playSound) player.start()
                if (vibrate) vibratePhone()
                return true
            }
            R.id.edit_account -> {
                val i = Intent(this@MenuActivity, UserDetails::class.java)
                startActivity(i)
                finish()
                return true
            }
            R.id.settings -> {
                val i = Intent(this@MenuActivity, SettingsActivity::class.java)
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
        fillFromDb()
    }

    private fun fillFromDb() {

        val docRef = db.collection("profiles").document(auth.currentUser!!.uid)
        docRef.get().addOnSuccessListener { documentSnapshot ->
            val profile = documentSnapshot.toObject(Profile::class.java)
            user_name.text = profile!!.name
            user.text = profile.name + " " + profile.surname
        }
    }

    private fun readValuesFromStorage() {

        val privateMode = 0
        val prefsFileName = "prefs"
        val theme = "theme"
        val sound = "sound"
        val chooseRingtone = "chooseRingtone"
        val vibrations = "vibrations"

        val prefs: SharedPreferences = getSharedPreferences(prefsFileName, privateMode)
        if (prefs.contains(theme) && prefs.contains(sound) && prefs.contains(sound) && prefs.contains(
                sound
            )
        ) {
            val themeController = ThemeController()
            setTheme(themeController.changeTheme(prefs))
            dark = prefs.getBoolean(theme, false)
            playSound = when (prefs.getBoolean(sound, false)) {
                true -> true
                false -> false
            }

            when (prefs.getInt(chooseRingtone, 1)) {
                1 -> {
                    player = MediaPlayer.create(this, R.raw.sound1)
                }
                2 -> {
                    player = MediaPlayer.create(this, R.raw.sound2)
                }
            }
            vibrate = when (prefs.getBoolean(vibrations, false)) {
                true -> true
                false -> false
            }
        } else {
            val editor = prefs.edit()
            editor.putBoolean(theme, false)
            editor.putBoolean(sound, false)
            editor.putInt(chooseRingtone, 1)
            editor.putBoolean(vibrations, false)
            editor.apply()
        }
    }

    private fun vibratePhone() {
        val vibrations = getSystemService(VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= 26) {
            vibrations.vibrate(
                VibrationEffect.createOneShot(
                    200,
                    VibrationEffect.DEFAULT_AMPLITUDE
                )
            )
        } else {
            vibrations.vibrate(200)
        }
    }
}
