package android.system.myapplication.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    fun dateFormatter(date: Date): String {
        val formatter = SimpleDateFormat("dd/M/yyyy HH:mm:ss", Locale.getDefault())
        formatter.timeZone = TimeZone.getDefault()
        return formatter.format(date)
    }

    fun formatCalendar(myCalendar: Calendar, year: Int, month: Int, day: Int): String {
        myCalendar.set(Calendar.YEAR, year)
        myCalendar.set(Calendar.MONTH, month)
        myCalendar.set(Calendar.DAY_OF_MONTH, day)
        val myFormat = "dd/M/yyyy"
        return SimpleDateFormat(myFormat, Locale.getDefault()).format(myCalendar.time)
    }
}