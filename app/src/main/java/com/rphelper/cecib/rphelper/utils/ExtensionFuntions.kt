package com.rphelper.cecib.rphelper.utils

import android.content.Context
import android.content.res.Resources

fun Context.resIdByName(resIdName: String?, resType: String): Int {
    resIdName?.let {
        return resources.getIdentifier(it, resType, packageName)
    }
    throw Resources.NotFoundException()
}