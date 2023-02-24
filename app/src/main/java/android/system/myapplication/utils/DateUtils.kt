package android.system.myapplication.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    fun dateFormatter(date: Date): String {
        val formatter = SimpleDateFormat("dd/M/yyyy HH:mm:ss", Locale.getDefault())
        formatter.timeZone = TimeZone.getDefault()
        return formatter.format(date)
    }
}