package com.example.meetexpress

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_menu.*
import androidx.drawerlayout.widget.DrawerLayout
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T

class MenuActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private val createFragment : CreateEventFragment = CreateEventFragment()
    private val findFragment : FindEventFragment = FindEventFragment()
    private val reviewFragment : ReviewEventsFragment = ReviewEventsFragment()
    private var drawerLayout : DrawerLayout? = null

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

            drawerLayout = findViewById(R.id.drawer_layout)
            val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
            bottomNavigationView.setOnNavigationItemSelectedListener(this)
            bottomNavigationView.selectedItemId = R.id.menu_create
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
        }
        return false
    }

    fun openDrawer() {
        drawerLayout?.openDrawer(nav)
    }
}
