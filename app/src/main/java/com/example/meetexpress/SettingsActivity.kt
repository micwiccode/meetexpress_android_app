package com.example.meetexpress

import android.app.TaskStackBuilder
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.*
import android.content.Intent.getIntent
import android.content.Intent
import android.content.res.Resources
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.preference.RingtonePreference
import android.util.Log
import android.widget.Toast
import com.google.android.material.internal.ContextUtils.getActivity


class SettingsActivity : AppCompatActivity() {

    private val privateMode = 0
    private val prefsFileName = "prefs"

    override fun onCreate(savedInstanceState: Bundle?) {
        val prefs: SharedPreferences = getSharedPreferences(prefsFileName, privateMode)
        val themeController = ThemeController()
        setTheme(themeController.changeTheme(prefs))
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        val actionBar = supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.settings, SettingsFragment())
            .commit()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val i = Intent(this@SettingsActivity, MenuActivity::class.java)
        startActivity(i)
    }

    class SettingsFragment : PreferenceFragmentCompat(){

        lateinit var themePref: SwitchPreferenceCompat
        lateinit var soundPref: SwitchPreferenceCompat
        lateinit var soundFilePref: ListPreference
        lateinit var vibrationsPref: SwitchPreferenceCompat
        private val privateMode = 0
        private val prefsFileName = "prefs"
        private val theme = "theme"
        private val sound = "sound"
        private val chooseRingtone = "chooseRingtone"
        private val vibrations = "vibrations"

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {

            setPreferencesFromResource(R.xml.root_preferences, rootKey)
            themePref = this!!.findPreference("theme")!!
            soundPref = this!!.findPreference("sound")!!
            soundFilePref = this!!.findPreference("chooseRingtone")!!
            vibrationsPref = this!!.findPreference("vibrations")!!

            getPreferences()

            val prefs: SharedPreferences =
                context!!.getSharedPreferences(prefsFileName, privateMode)
            val editor = prefs.edit()

            themePref.setOnPreferenceClickListener {
                editor.putBoolean(theme, themePref.isChecked)
                editor.apply()
                true
            }

            soundPref.setOnPreferenceClickListener {
                editor.putBoolean(sound, soundPref.isChecked)
                editor.apply()
                true
            }

            soundFilePref.setOnPreferenceChangeListener { preference, value ->
                val index = soundFilePref.findIndexOfValue(value.toString())
                editor.putInt(chooseRingtone, (soundFilePref.entryValues[index]).toString().toInt())
                editor.apply()
                true
            }

            vibrationsPref.setOnPreferenceClickListener {
                editor.putBoolean(vibrations, vibrationsPref.isChecked)
                editor.apply()
                true
            }
        }

        private fun getPreferences() {

            val prefs: SharedPreferences =
                context!!.getSharedPreferences(prefsFileName, privateMode)

            themePref.isChecked = prefs.getBoolean(theme, false)
            soundPref.isChecked = prefs.getBoolean(sound, false)
            soundFilePref.value = prefs.getInt(chooseRingtone, 1).toString()
            vibrationsPref.isChecked = prefs.getBoolean(vibrations, false)
        }

        override fun onResume() {
            super.onResume()
            getPreferences()
        }
    }
}