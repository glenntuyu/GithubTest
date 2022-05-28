package com.astro.test.glenntuyu.utils

import com.google.gson.Gson
import java.io.File

/**
 * Created by glenntuyu on 28/05/2022.
 */
inline fun <reified T> String.jsonToObject(): T {
    val systemClassLoader = ClassLoader.getSystemClassLoader()
    val urlResource = systemClassLoader.getResource(this)
    val urlResourcePath = urlResource.path
    val fileFromUrlResourcePath = File(urlResourcePath)
    val jsonString = String(fileFromUrlResourcePath.readBytes())

    return Gson().fromJson(jsonString, T::class.java)
}