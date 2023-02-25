package android.system.myapplication

import android.os.Bundle
import android.system.myapplication.models.Todo
import android.system.myapplication.utils.BackendClient
import android.system.myapplication.utils.BackendClient.ping
import android.system.myapplication.utils.BackendClient.sendTodo
import android.system.myapplication.utils.showToast
import android.system.myapplication.utils.todosArray
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class Settings : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        supportActionBar?.hide()

        val bDeleteDB = findViewById<Button>(R.id.bDeleteDB)
        val bPing = findViewById<Button>(R.id.bPing)
        val bSend = findViewById<Button>(R.id.bSend)
        val bSincroDa = findViewById<Button>(R.id.bSincroDa)

        bDeleteDB.setOnClickListener {
            val todos = Database.getDatabase(baseContext).boxFor(Todo::class.java)
            todos.removeAll()
        }

        bPing.setOnClickListener {
            ping()
        }

        bSend.setOnClickListener {

            val body = todosArray(Application.todos.all)

            for (i in 0 until body.length()) {
                val todoJson = body.getJSONObject(i)

                sendTodo(todoJson)
            }

        }

        bSincroDa.setOnClickListener {
            BackendClient.getTodos(
                {
                    Application.todos.put(it)
                },
                {
                    runOnUiThread { showToast("errore", this) }
                }
            )
        }

    }
}
