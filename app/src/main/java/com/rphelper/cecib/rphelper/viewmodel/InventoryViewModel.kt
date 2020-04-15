package com.rphelper.cecib.rphelper.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rphelper.cecib.rphelper.R
import com.rphelper.cecib.rphelper.dto.Object
import com.rphelper.cecib.rphelper.utils.FileUtils
import org.json.JSONObject

class InventoryViewModel (val context: Context) : ViewModel(){
    val _money = MutableLiveData<Int>()
    val money : LiveData<Int> get() = _money
    init {
        val leftHandString = JSONObject(getInventoryString()).get("money").toString()
         _money.value = Gson().fromJson<Int>(leftHandString, Int::class.java)
    }

    val _objects = MutableLiveData<List<Object>>()
    val objects : LiveData<List<Object>> get() = _objects
    init {
        val objectsString = JSONObject(getInventoryString()).get("item").toString()
        if(!objectsString.equals(context.getString(R.string.empty_json))) {
            val sType = object : TypeToken<List<Object>>() { }.type
            _objects.value = Gson().fromJson<List<Object>>(objectsString, sType)
        }
    }

    fun getInventoryString():String{
        return FileUtils.readJsonAsset(context,"inventory.json")
    }
}