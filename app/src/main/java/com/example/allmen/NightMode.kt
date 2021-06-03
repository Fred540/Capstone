package com.example.allmen

import android.app.Application
import android.preference.PreferenceManager


class NightMode : Application() {

    private var isNightModeEnabled = false

    override fun onCreate() {
        super.onCreate()
        singleton = this
        val mPrefs = PreferenceManager.getDefaultSharedPreferences(this)
        isNightModeEnabled = mPrefs.getBoolean(NIGHT_MODE, false)
    }

    fun isNightModeEnabled(): Boolean {
        return isNightModeEnabled
    }

    fun setIsNightModeEnabled(isNightModeEnabled: Boolean) {
        this.isNightModeEnabled = isNightModeEnabled
        val mPrefs = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = mPrefs.edit()
        editor.putBoolean(NIGHT_MODE, isNightModeEnabled)
        editor.apply()
    }

    companion object {
        const val NIGHT_MODE = "NIGHT_MODE"
        private var singleton: NightMode? = null
        val instance: NightMode?
            get() {
                if (singleton == null) {
                    singleton = NightMode()
                }
                return singleton
            }
    }
}