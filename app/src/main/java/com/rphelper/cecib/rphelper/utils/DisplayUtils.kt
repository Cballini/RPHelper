package com.rphelper.cecib.rphelper.utils

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import com.rphelper.cecib.rphelper.Preferences
import com.rphelper.cecib.rphelper.R
import com.rphelper.cecib.rphelper.Services
import com.rphelper.cecib.rphelper.component.IndicComponent

object DisplayUtils {
    @JvmStatic
    fun hideKeyboard(view: View, context: Context) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    @JvmStatic
    fun stringBonus(bonus :Int) : String{
        val sign = if (bonus>=0) "+ " else ""
        return "(" + sign + bonus.toString() + ")"
    }

    @JvmStatic
    fun stringBonus(bonus :String) : String{
        val string = if (bonus.isNotEmpty()) "(" + bonus.toString() + ")" else ""
        return string
    }

    @JvmStatic
    fun displayEditIndicBonusDialog(context: Context, txt: String, pref :String, toDo: () -> Unit){
        val builder = AlertDialog.Builder(context)
        val dialogLayout = LayoutInflater.from(context).inflate(R.layout.dialog_edit_indic, null)
        builder.setView(dialogLayout)
        builder.setTitle(context.getString(R.string.bonusTitle))
        val txtView = dialogLayout.findViewById<TextView>(R.id.edit_indic_txt)
        txtView.text = txt
        val editText = dialogLayout.findViewById<EditText>(R.id.edit_indic_value)

        builder.setPositiveButton(context.getString(R.string.ok)) { dialogInterface, i ->
            dialogInterface.dismiss()

            val sharedPref: SharedPreferences = context!!.getSharedPreferences(pref, Preferences.PRIVATE_MODE)
            val editor = sharedPref.edit()
            var value = 0
            if (editText.text.isNotEmpty()) { value = editText.text.toString().toInt() }
            editor.putInt(pref, value)
            editor.apply()
            toDo()
        }

        builder.setNeutralButton(context.getString(R.string.reset)) { dialogInterface, i ->
            dialogInterface.dismiss()
            val sharedPref: SharedPreferences = context!!.getSharedPreferences(pref, Preferences.PRIVATE_MODE)
            val editor = sharedPref.edit()
            editor.putInt(pref, 0)
            editor.apply()
            toDo()
        }
        builder.show()
    }
}