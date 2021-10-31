package test.zadanie.com

import android.content.Context
import android.content.SharedPreferences

class Prefs(context: Context) {
    private val PREFERENCE_NAME="SharedPreference"
    private val preference: SharedPreferences =context.getSharedPreferences(PREFERENCE_NAME,Context.MODE_PRIVATE)


    fun setString(key:String, value:String) {
        val edit: SharedPreferences.Editor = preference.edit()
        edit.putString(key, value)
        edit.apply()
    }

    fun setBoolean(key:String, value:Boolean) {
        val edit: SharedPreferences.Editor = preference.edit()
        edit.putBoolean(key, value)
        edit.apply()
    }

    fun getString(key:String): String
    {
        return preference.getString(key,null).toString()
    }

    fun getBoolean(key:String):Boolean
    {
        return preference.getBoolean(key, true)
    }
}