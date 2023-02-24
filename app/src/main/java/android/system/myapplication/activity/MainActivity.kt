package android.system.myapplication.activity

import android.content.Intent
import android.os.Bundle
import android.system.myapplication.Application
import android.system.myapplication.CustomAdapter
import android.system.myapplication.R
import android.system.myapplication.models.Todo_.date_add
import android.system.myapplication.models.Todo_.done
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: CustomAdapter

    lateinit var list: ListView
    var on = false

    private fun refreshList() {
        val todo = Application.todos
            .query()
            .order(done)
            .orderDesc(date_add)
            .build()
            .findLazy()

        adapter = CustomAdapter(this@MainActivity, R.layout.rowcustom, todo, ::refreshList)
        list.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        on = intent.getBooleanExtra("on", false)
        if (on) {
            refreshList()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        list = findViewById(R.id.list1)

        val bAddTodo = findViewById<MaterialButton>(R.id.bAddTodo)
        val bSettings = findViewById<MaterialButton>(R.id.bSettings)
        val bRefresh = findViewById<MaterialButton>(R.id.bRefresh)

        refreshList()

        list.setOnItemClickListener { parent, _, position, _ ->
            val todoItem = parent.getItemAtPosition(position)
            Toast.makeText(this, todoItem.toString(), Toast.LENGTH_SHORT).show()
        }

        bAddTodo.setOnClickListener {
            val intent = Intent(this, AddTodo::class.java)
            startActivity(intent)
        }

        bSettings.setOnClickListener {
            val intent = Intent(this, Settings::class.java)
            startActivity(intent)
        }

        bRefresh.setOnClickListener {
            refreshList()
        }
    }
}
