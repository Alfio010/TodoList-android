package android.system.myapplication

import android.content.Intent
import android.os.Bundle
import android.system.myapplication.models.Todo
import android.system.myapplication.utils.BackendClient.sendTodo
import android.system.myapplication.utils.lastTodo
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import io.objectbox.Box
import java.util.*

class addTodo : AppCompatActivity() {
    private lateinit var todos: Box<Todo>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_todo)
        supportActionBar?.hide()

        val bAddd: Button = findViewById(R.id.bAddd)
        val etName: EditText = findViewById(R.id.etName)
        val etDescription: EditText = findViewById(R.id.etDescription)

        todos = Database.getDatabase(baseContext).boxFor(Todo::class.java)

        val spinner: Spinner = findViewById(R.id.spinner)

        ArrayAdapter.createFromResource(
            this,
            R.array.priority_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice)
            spinner.adapter = adapter
        }



        bAddd.setOnClickListener {

            val input = etName.text.toString().trim()

            if (input.isBlank()) {
                Toast.makeText(this, "Inserire il nome", Toast.LENGTH_SHORT).show()
            } else {

                var text = spinner.selectedItem.toString()

                if (text == null) {
                    text = "Normale"
                }

                var priorityy = 0
                when (text) {
                    "Bassa" -> priorityy = 1
                    "Normale" -> priorityy = 2
                    "Alta" -> priorityy = 3
                }

                val currentDate = Date()

                todos.put(
                    Todo(
                        0,
                        etName.text.toString(),
                        etDescription.text.toString(),
                        0,
                        priorityy,
                        currentDate,
                        null
                    )
                )

                val todo = lastTodo()

                val bodyElement = todo?.toJson()

                if (bodyElement != null) {
                    sendTodo(bodyElement)
                }

                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("on", true)
                finish()
                startActivity(intent)

            }
        }
    }
}
