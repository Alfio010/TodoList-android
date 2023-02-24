package android.system.myapplication.utils

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

fun showToast(text: String, context: AppCompatActivity) {
    Toast(context).apply { setText(text) }.show()
}
