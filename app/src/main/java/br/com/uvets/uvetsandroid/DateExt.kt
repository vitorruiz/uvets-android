package br.com.uvets.uvetsandroid

import java.text.SimpleDateFormat
import java.util.*

fun Date.toISOString(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd")
    return sdf.format(this)
}