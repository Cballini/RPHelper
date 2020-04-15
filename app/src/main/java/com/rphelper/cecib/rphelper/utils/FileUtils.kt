package com.rphelper.cecib.rphelper.utils

import android.content.Context
import android.content.res.Resources
import com.google.gson.JsonParser
import com.google.gson.JsonSerializer
import org.json.JSONObject
import java.io.FileReader
import java.io.IOException

object FileUtils {

    @JvmStatic
    fun readJsonAsset(context: Context, fileName : String): String {
      return context.assets.open(fileName).bufferedReader().use { it.readText() }
    }

}