package br.com.uvets.uvetsandroid.utils

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.text.TextUtils
import android.text.format.DateFormat
import android.widget.TextView
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class PickerUtils {

    companion object {
        fun showTimePickerDialog(context: Context, viewToUpdate: TextView, initWithCurrentTime: Boolean) {
            val hourString = viewToUpdate.text.toString()
            val hour: Int
            val minute: Int
            if (TextUtils.isEmpty(hourString)) {
                if (initWithCurrentTime) {
                    val mCurrentTime = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
                    hour = mCurrentTime.get(Calendar.HOUR_OF_DAY)
                    minute = mCurrentTime.get(Calendar.MINUTE)
                } else {
                    hour = 0
                    minute = 0
                }
            } else {
                hour = Integer.parseInt(hourString.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0])
                minute = Integer.parseInt(hourString.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1])
            }
            val timePickerDialog: TimePickerDialog
            timePickerDialog = TimePickerDialog(
                context,
                TimePickerDialog.OnTimeSetListener { timePicker, selectedHour, selectedMinute ->
                    viewToUpdate.text = String.format("%02d:%02d", selectedHour, selectedMinute)
                },
                hour,
                minute,
                true
            )
            timePickerDialog.show()
        }

        fun showDatePickerDialog(context: Context, viewToUpdate: TextView, blockFutureDate: Boolean = false) {
            val dateFormatter = DateFormat.getDateFormat(context)
            val dateString = viewToUpdate.text.toString()
            val year: Int
            val month: Int
            val day: Int
            val newCalendar: Calendar
            if (TextUtils.isEmpty(dateString)) {
                newCalendar = Calendar.getInstance()
            } else {
                newCalendar = GregorianCalendar()
                try {
                    val date = dateFormatter.parse(dateString)
                    newCalendar.setTime(date)
                } catch (e: ParseException) {
                    e.printStackTrace()
                }

            }
            year = newCalendar.get(Calendar.YEAR)
            month = newCalendar.get(Calendar.MONTH)
            day = newCalendar.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog: DatePickerDialog
            datePickerDialog =
                DatePickerDialog(context, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                val newDate = Calendar.getInstance()
                newDate.set(year, monthOfYear, dayOfMonth)
                viewToUpdate.text = dateFormatter.format(newDate.time)
            }, year, month, day)

            if (blockFutureDate) {
                datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
            }

            datePickerDialog.show()
        }

        fun extractDateTime(context: Context, dateString: String, hourString: String): Date {
            val dateFormat = DateFormat.getDateFormat(context)
            dateFormat.timeZone = TimeZone.getDefault()
            val timeFormat = SimpleDateFormat("HH:mm")

            var date = Date()
            try {
                date = dateFormat.parse(dateString)
                val time = timeFormat.parse(hourString)
                date.time = date.time + time.time
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            return date
        }
    }
}