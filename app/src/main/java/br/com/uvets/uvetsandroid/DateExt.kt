package br.com.uvets.uvetsandroid

import java.text.SimpleDateFormat
import java.util.*

private val isoFormat = SimpleDateFormat("yyyy-MM-dd")
private val monthStringFormat = SimpleDateFormat("MMMM")
private val onlyDayStringFormat = SimpleDateFormat("dd")

fun Date.toISOString(): String = isoFormat.format(this)

fun Date.toMonthString(): String = monthStringFormat.format(this)

fun Date.toOnlyDayString(): String = onlyDayStringFormat.format(this)