package android.system.myapplication.activity

import android.os.Bundle
import android.system.myapplication.Database
import android.system.myapplication.R
import android.system.myapplication.models.Todo
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class Settings : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        supportActionBar?.hide()

        val bDeleteDB = findViewById<MaterialButton>(R.id.bDeleteDB)

        bDeleteDB.setOnClickListener {
            val todos = Database.getDatabase(baseContext).boxFor(Todo::class.java)
            todos.removeAll()
        }
    }
}