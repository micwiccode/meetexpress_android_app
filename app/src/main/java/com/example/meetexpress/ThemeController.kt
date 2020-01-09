package com.example.meetexpress

import android.content.SharedPreferences

class ThemeController {

    private val theme = "theme"

    fun changeTheme(prefs:SharedPreferences): Int {

        return when(prefs.getBoolean(theme, false)){
            false-> R.style.AppTheme
            true-> R.style.AppThemeDark
        }
    }
}