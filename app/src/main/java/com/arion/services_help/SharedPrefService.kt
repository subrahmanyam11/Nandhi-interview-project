package com.arion.services_help

import android.content.Context
import android.content.SharedPreferences

class SharedPrefService {

    val adityanLoginFile = "com.arion.credentials"
    private val TOCKEN = "id_key"
    private val NAME = "name"
    private val MOBILE = "mobile"

    fun AddLoginDetails(context: Context, id: String, name: String, mobile: String){

        val sharePref: SharedPreferences? = context.getSharedPreferences(adityanLoginFile, Context.MODE_PRIVATE)
        val editor:SharedPreferences.Editor =  sharePref!!.edit()
        editor.putString(TOCKEN, id)
        editor.putString(NAME, name)
        editor.putString(MOBILE, mobile)
        editor.apply()
        editor.commit()

    }

    // gettting values..

    fun getUserTocken(context: Context): String {
        val sharePref: SharedPreferences? = context.getSharedPreferences(adityanLoginFile, Context.MODE_PRIVATE)
        return sharePref?.getString(TOCKEN, "null")!!
    }

    fun getUserName(context: Context): String {
        val sharePref: SharedPreferences? = context.getSharedPreferences(adityanLoginFile, Context.MODE_PRIVATE)
        return sharePref?.getString(NAME, "null")!!
    }

    fun getMobileNumber(context: Context): String {
        val sharePref: SharedPreferences? = context.getSharedPreferences(adityanLoginFile, Context.MODE_PRIVATE)
        return sharePref?.getString(MOBILE, "null")!!
    }

    // operations..
    fun LogoutUser(context: Context) {
        val sharePref: SharedPreferences? = context.getSharedPreferences(adityanLoginFile, Context.MODE_PRIVATE)
        val editor = sharePref!!.edit()
        editor.clear()
        editor.apply()
    }
    fun isLogedIn(context: Context): Boolean{
        val sharePref: SharedPreferences? = context.getSharedPreferences(adityanLoginFile, Context.MODE_PRIVATE)
        val id: String =  sharePref?.getString(TOCKEN, "null")!!
        if (id.equals("null")) return false else return true
    }

}