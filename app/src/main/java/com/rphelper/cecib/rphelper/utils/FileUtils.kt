package com.rphelper.cecib.rphelper.utils

import android.content.Context
import android.content.res.Resources
import com.google.gson.JsonParser
import com.google.gson.JsonSerializer
import com.rphelper.cecib.rphelper.R
import org.json.JSONObject
import java.io.*
import java.nio.charset.Charset

object FileUtils {

    @JvmStatic
    fun readJsonAsset(context: Context, fileName : String): String {
      return context.assets.open(fileName).bufferedReader().use { it.readText() }
    }

    @JvmStatic
    fun readJsonFile(context: Context, fileName : String): String {
        val directory = context.filesDir
        var fileObject = File(directory, fileName)
        // create a new file
        if(fileObject.createNewFile()){
            when(fileName){
                context.getString(R.string.file_character) -> fileObject.writeText(readJsonAsset(context, context.getString(R.string.file_empty_character)))
                context.getString(R.string.file_equipment) -> fileObject.writeText(readJsonAsset(context, context.getString(R.string.file_empty_equipment)))
                context.getString(R.string.file_fight) -> fileObject.writeText(readJsonAsset(context, context.getString(R.string.file_empty_fight)))
                context.getString(R.string.file_spells) -> fileObject.writeText(readJsonAsset(context, context.getString(R.string.file_empty_spells)))
                context.getString(R.string.file_inventory) -> fileObject.writeText(readJsonAsset(context, context.getString(R.string.file_empty_inventory)))
                else -> fileObject.writeText("{}")
            }
        }

        var ins:InputStream = fileObject.inputStream()
        // read contents of IntputStream to String
        var content = ins.readBytes().toString(Charset.defaultCharset())
        return content
    }

    @JvmStatic
    fun editJsonFile(context: Context, fileName: String, content :String){
        val directory = context.filesDir
        var fileObject = File(directory, fileName)
        fileObject.writeText(content)
    }

}