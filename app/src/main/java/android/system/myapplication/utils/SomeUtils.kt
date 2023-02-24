package android.system.myapplication.utils

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

object SomeUtils {
    fun showToast(text: String, context: AppCompatActivity) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show()
    }
}